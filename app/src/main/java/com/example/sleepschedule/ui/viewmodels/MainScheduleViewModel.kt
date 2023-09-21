package com.example.sleepschedule.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sleepschedule.ui.utils.DialogType
import com.ulises.dispatcher_core.ScheduleDispatchers
import com.ulises.usecases.DeleteScheduleEventUseCase
import com.ulises.usecases.GetAllScheduledEventsUseCase
import com.ulises.usecases.UpdateScheduleEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import models.CardFace
import models.ScheduledEvent
import javax.inject.Inject

@HiltViewModel
class MainScheduleViewModel @Inject constructor(
    private val dispatchers: ScheduleDispatchers,
    private val getAllScheduledEventsUseCase: GetAllScheduledEventsUseCase,
    private val deleteScheduleEventUseCase: DeleteScheduleEventUseCase,
    private val updateScheduleEventUseCase: UpdateScheduleEventUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    data class UiState(
        val scheduleEvents: List<ScheduledEvent>? = null,
        val isLoading: Boolean = false,
        val showDialogDelete: Boolean = false,
        val showDialogRating: Boolean = false,
        val selectedEvent: ScheduledEvent? = null
    )

    init {
        viewModelScope.launch(dispatchers.io) {
            getAllScheduledEventsUseCase()
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch { _uiState.update { it.copy(isLoading = false) } }
                .collect { list ->
                    _uiState.update { it.copy(scheduleEvents = list, isLoading = false) }
                }
        }
    }

    fun onDialogCloseVisibilityChange(
        dialogType: DialogType,
        isVisible: Boolean,
        event: ScheduledEvent? = null
    ) {
        when (dialogType) {
            is DialogType.Rating -> {
                _uiState.update { it.copy(showDialogRating = isVisible, selectedEvent = event) }
            }
            is DialogType.Delete -> {
                _uiState.update { it.copy(showDialogDelete = isVisible, selectedEvent = event) }
            }
        }
    }

    fun onDeleteScheduleEvent(eventId: String?) {
        viewModelScope.launch(dispatchers.io) {
            if (!eventId.isNullOrEmpty()) {
                runCatching {
                    deleteScheduleEventUseCase(eventId)
                }.onFailure {

                }.onSuccess {
                    onDialogCloseVisibilityChange(DialogType.Delete, false)
                }
            }
        }
    }

    fun onUpdateRating(event: ScheduledEvent?, newRating: Int) {
        viewModelScope.launch(dispatchers.io) {
            event?.also {
                runCatching {
                    updateScheduleEventUseCase(it.id, newRating)
                }.onFailure {

                }.onSuccess {
                    onDialogCloseVisibilityChange(DialogType.Rating, false)
                }
            }
        }
    }

    fun onClickItemForRotation(item: ScheduledEvent) {
        viewModelScope.launch(dispatchers.default) {
            val items = _uiState.value.scheduleEvents?.toMutableList() ?: return@launch
            val itemSelected = items.indexOf(item)
            items[itemSelected] = with(items[itemSelected]) {
                copy(cardFace = if (cardFace == CardFace.FRONT) CardFace.BACK else CardFace.FRONT)
            }
            _uiState.update { it.copy(scheduleEvents = items.toList()) }
        }
    }
}