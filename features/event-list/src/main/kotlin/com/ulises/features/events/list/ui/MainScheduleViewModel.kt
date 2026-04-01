package com.ulises.features.events.list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.dispatcher_core.ScheduleDispatchers
import com.ulises.events.DeleteScheduleEventUseCase
import com.ulises.events.GetAllScheduledEventsUseCase
import com.ulises.events.UpdateScheduleEventUseCase
import com.ulises.features.events.list.models.Actions
import com.ulises.features.events.list.models.UiState
import com.ulises.features.events.list.models.YearsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import models.ScheduledEvent
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainScheduleViewModel @Inject constructor(
    private val dispatchers: ScheduleDispatchers,
    private val getAllScheduledEventsUseCase: GetAllScheduledEventsUseCase,
    private val deleteScheduleEventUseCase: DeleteScheduleEventUseCase,
    private val updateScheduleEventUseCase: UpdateScheduleEventUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
    private var cacheMap: Map<String, List<ScheduledEvent>> = emptyMap()

    init {
        getData()
    }

    fun onHandleIntent(action: Actions.Interaction) {
        when (action) {
            is Actions.Interaction.ClickItem -> onExpandCollapseItem(action.item)
            Actions.Interaction.ClearError -> onErrorDisplayed()
            is Actions.Interaction.UpdateRating -> onUpdateRating(
                _uiState.value.selectedEvent,
                action.newRating
            )

            is Actions.Interaction.ChangeDeleteDialogState -> {
                _uiState.update {
                    it.copy(showDialogDelete = action.isVisible, selectedEvent = action.item)
                }
            }

            is Actions.Interaction.ChangeRateDialogState -> {
                _uiState.update {
                    it.copy(
                        showDialogRating = action.isVisible,
                        selectedEvent = action.item,
                        selectedKid = action.kid
                    )
                }
            }

            is Actions.Interaction.ShowDeleteDialog -> showDeleteDialog(action.event)
            Actions.Interaction.DismissDeleteDialog -> dismissDeleteDialog()
            Actions.Interaction.DeleteItem -> onDeleteScheduleEvent(_uiState.value.deleteDialogState.event)
            is Actions.Interaction.ChangeSelectedYear -> onChangeSelectedYear(action.year)
            Actions.Interaction.DismissMessage -> dismissMessage()
            is Actions.Interaction.ShowMessage -> showMessage(action.message)
        }
    }

    private fun showMessage(message: String) {
        _uiState.update { it.copy(message = message) }
    }

    private fun dismissMessage() {
        _uiState.update { it.copy(message = "") }
    }

    private fun showDeleteDialog(event: ScheduledEvent) {
        _uiState.update {
            it.copy(
                deleteDialogState = it.deleteDialogState.copy(
                    isVisible = true,
                    event = event,
                )
            )
        }
    }

    private fun dismissDeleteDialog() {
        _uiState.update {
            it.copy(
                deleteDialogState = it.deleteDialogState.copy(
                    isVisible = false,
                    event = null,
                )
            )
        }
    }

    private fun getData() {
        viewModelScope.launch(dispatchers.io) {
            getAllScheduledEventsUseCase()
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .map { it.groupBy { it.date.split("-")[0] } }
                .onEach { cacheMap = it }
                .map { it.getYearsData() }
                .onEach { Timber.d("$it") }
                .catch {
                    Timber.e(it)
                    _uiState.update { it.copy(isLoading = false) }
                }
                .collect { list ->
                    _uiState.update {
                        it.copy(
                            scheduleEvents = cacheMap[list.initialYear],
                            isLoading = false,
                            years = list.yearsList,
                            selectedYear = list.initialYear,
                        )
                    }
                }
        }
    }

    private fun Map<String, List<ScheduledEvent>>.getYearsData(): YearsData {
        val currentYear = LocalDate.now().year.toString()
        //  Only the first time
        val selectedYear = when {
            uiState.value.selectedYear.isBlank() -> currentYear
            isEmpty() -> currentYear
            !containsKey(uiState.value.selectedYear) -> currentYear
            else -> uiState.value.selectedYear
        }
        //  Add current year even if no elements present
        val years = (keys + currentYear).sortedByDescending { it }
        return YearsData(
            initialYear = selectedYear,
            yearsList = years,
        )
    }

    private fun onChangeSelectedYear(newSelection: String) {
        _uiState.update {
            it.copy(
                selectedYear = newSelection,
                scheduleEvents = cacheMap[newSelection],
            )
        }
    }

    private fun onDeleteScheduleEvent(event: ScheduledEvent?) {
        val event = _uiState.value.deleteDialogState.event
        viewModelScope.launch(dispatchers.io) {
            runCatching {
                requireNotNull(event?.id)
                deleteScheduleEventUseCase(event.id)
            }.onFailure { error ->
                Timber.e(error, "Error deleting event: $event")
                onHandleIntent(Actions.Interaction.DismissDeleteDialog)
                _uiState.update { it.copy(message = error.localizedMessage) }
            }.onSuccess {
                Timber.d("Deleted event: $event")
                onHandleIntent(Actions.Interaction.DismissDeleteDialog)
            }
        }
    }

    private fun onUpdateRating(event: ScheduledEvent?, newRating: Int) {
        viewModelScope.launch(dispatchers.io) {
            event?.also {
                runCatching {
                    val newVersion = _uiState.value.selectedKid
                    if (newVersion == null) {
                        updateScheduleEventUseCase(it.id, newRating)
                    } else {
                        val kidIndex = getIndexForKid(event)
                        Timber.d("Kid data: $newVersion, index: $kidIndex")
                        updateScheduleEventUseCase(it.id, newRating, kidIndex)
                    }
                }.onFailure { error ->
                    Timber.e(error, "Error updating the rating to :$event")
                    _uiState.update { it.copy(error = error.localizedMessage) }
                    onHandleIntent(Actions.Interaction.ChangeRateDialogState(false, null, null))
                }.onSuccess {
                    Timber.d("Rating updated: $event")
                    onHandleIntent(Actions.Interaction.ChangeRateDialogState(false, null, null))
                }
            }
        }
    }

    private fun onExpandCollapseItem(item: ScheduledEvent) {
        viewModelScope.launch(dispatchers.default) {
            val items = _uiState.value.scheduleEvents?.toMutableList() ?: return@launch
            val itemSelected = items.indexOf(item)
            items[itemSelected] = with(items[itemSelected]) {
                copy(isExpanded = !this.isExpanded)
            }
            _uiState.update { it.copy(scheduleEvents = items.toList()) }
        }
    }

    private fun onErrorDisplayed() {
        _uiState.update { it.copy(error = null) }
    }

    private fun getIndexForKid(event: ScheduledEvent): Int {
        return event.selectedKids.indexOf(_uiState.value.selectedKid)
    }
}