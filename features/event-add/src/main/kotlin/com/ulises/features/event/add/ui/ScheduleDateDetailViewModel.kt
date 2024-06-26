package com.ulises.features.event.add.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.common.time.utils.TimeHelper.toISODate
import com.ulises.common.time.utils.TimeHelper.toLocalDate
import com.ulises.dispatcher_core.ScheduleDispatchers
import com.ulises.events.AddScheduledEventUseCase
import com.ulises.features.event.add.models.UiState
import com.ulises.usecase.session.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import models.AvailableKids
import models.Kid
import outcomes.OutcomeScheduledEvent
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ScheduleDateDetailViewModel @Inject constructor(
    private val dispatchers: ScheduleDispatchers,
    private val addScheduledEventUseCase: AddScheduledEventUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onDateDialogVisibilityChange(visible: Boolean) {
        _uiState.update { it.copy(isDateDialogVisible = visible) }
    }

    fun onUpdateTextField(type: TextFieldType, text: String) {
        when (type) {
            is TextFieldType.CreatedBy -> {
                _uiState.update { it.copy(createdText = text) }
                validateInputsFilled()
            }
            is TextFieldType.Comment -> {
                if (text.length <= MAX_COMMENT_LENGTH) {
                    _uiState.update { it.copy(comments = text) }
                }
            }
        }
    }

    fun onUpdateSelectedKid(kids: AvailableKids) {
        when (kids) {
            AvailableKids.Ambos -> {
                _uiState.update { it.copy(selectedKids = listOf(AvailableKids.Renata.name, AvailableKids.Lando.name)) }
            }
            else -> {
                _uiState.update { it.copy(selectedKids = listOf(kids.name)) }
            }
        }
        validateInputsFilled()
    }

    fun onDateSelected(millis: Long?) {
        viewModelScope.launch(dispatchers.main) {
            runCatching {
                checkNotNull(millis)
                millis.toLocalDate()
            }.onFailure {
                Timber.e(it, "Error parsing Date")
            }.onSuccess { date ->
                Timber.d("New selected time: $date")
                _uiState.update {
                    it.copy(
                        isDateDialogVisible = false,
                        selectedDate = date
                    )
                }
            }
        }
    }

    fun onAddSchedule() {
        viewModelScope.launch(dispatchers.main) {
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                val user = getCurrentUserUseCase()
                val scheduleEvent = OutcomeScheduledEvent(
                    id = Date().time.toString(),
                    date = _uiState.value.selectedDate.toISODate(),
                    createdBy = _uiState.value.createdText.trim(),
                    createdOn = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    rating = 0,
                    comments = _uiState.value.comments,
                    createdById = user?.id ?: "Unknown",
                    selectedKids = _uiState.value.selectedKids.map { Kid(it, 0) },
                )
                addScheduledEventUseCase(scheduleEvent)
                scheduleEvent
            }.onSuccess { event ->
                Timber.d("Event created: $event")
                _uiState.update { it.copy(isLoading = false, addComplete = true) }
            }.onFailure {
                Timber.e(it, "Error creating this event")
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun validateInputsFilled() {
        val isReady = _uiState.value.createdText.trim().isNotBlank() && _uiState.value.selectedKids.isNotEmpty()
        _uiState.update { it.copy(isReadyToSend = isReady) }
    }

    companion object {
        const val MAX_COMMENT_LENGTH = 250
    }
}

sealed interface TextFieldType {
    data object CreatedBy : TextFieldType
    data object Comment : TextFieldType
}