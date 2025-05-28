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

            Actions.Interaction.DeleteItem -> {
                _uiState.value.selectedEvent?.also { onDeleteScheduleEvent(it.id) }
            }

            is Actions.Interaction.ChangeSelectedYear -> onChangeSelectedYear(action.year)
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
        val selectedYear = uiState.value.selectedYear.ifBlank {
            val currentYear = LocalDate.now().year.toString()
            if (isNotEmpty()) this[currentYear]?.let { currentYear } ?: keys.first() else ""
        }
        //
        return YearsData(
            initialYear = selectedYear,
            yearsList = if (isEmpty()) emptyList() else keys.toList(),
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

    private fun onDeleteScheduleEvent(eventId: String) {
        viewModelScope.launch(dispatchers.io) {
            runCatching {
                deleteScheduleEventUseCase(eventId)
            }.onFailure { error ->
                Timber.e(error, "Error deleting event: $eventId")
                _uiState.update { it.copy(error = error.localizedMessage) }
            }.onSuccess {
                Timber.d("Deleted event: $eventId")
                onHandleIntent(Actions.Interaction.ChangeDeleteDialogState(false, null))
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