package com.ulises.features.events.list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.dispatcher_core.ScheduleDispatchers
import com.ulises.events.DeleteScheduleEventUseCase
import com.ulises.events.GetAllScheduledEventsUseCase
import com.ulises.events.UpdateScheduleEventUseCase
import com.ulises.features.events.list.models.Intents
import com.ulises.features.events.list.models.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import models.ScheduledEvent
import timber.log.Timber
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

    init {
        getData()
    }

    fun onHandleIntent(intent: Intents) {
        when (intent) {
            is Intents.ClickItem -> onExpandCollapseItem(intent.item)
            Intents.ClearError -> onErrorDisplayed()
            is Intents.UpdateRating -> onUpdateRating(
                _uiState.value.selectedEvent,
                intent.newRating
            )

            is Intents.ChangeDeleteDialogState -> {
                _uiState.update {
                    it.copy(showDialogDelete = intent.isVisible, selectedEvent = intent.item)
                }
            }

            is Intents.ChangeRateDialogState -> {
                _uiState.update {
                    it.copy(
                        showDialogRating = intent.isVisible,
                        selectedEvent = intent.item,
                        selectedKid = intent.kid
                    )
                }
            }

            Intents.DeleteItem -> {
                _uiState.value.selectedEvent?.also { onDeleteScheduleEvent(it.id) }
            }
        }
    }

    private fun getData() {
        viewModelScope.launch(dispatchers.io) {
            getAllScheduledEventsUseCase()
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch { _uiState.update { it.copy(isLoading = false) } }
                .collect { list ->
                    _uiState.update { it.copy(scheduleEvents = list, isLoading = false) }
                }
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
                onHandleIntent(Intents.ChangeDeleteDialogState(false, null))
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
                    onHandleIntent(Intents.ChangeRateDialogState(false, null, null))
                }.onSuccess {
                    Timber.d("Rating updated: $event")
                    onHandleIntent(Intents.ChangeRateDialogState(false, null, null))
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