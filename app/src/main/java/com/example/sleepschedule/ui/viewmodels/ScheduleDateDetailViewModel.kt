package com.example.sleepschedule.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.common.time.utils.TimeHelper.toISODate
import com.ulises.common.time.utils.TimeHelper.toLocalDate
import com.ulises.dispatcher_core.ScheduleDispatchers
import com.ulises.events.AddScheduledEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import outcomes.OutcomeScheduledEvent
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ScheduleDateDetailViewModel @Inject constructor(
    private val dispatchers: ScheduleDispatchers,
    private val addScheduledEventUseCase: AddScheduledEventUseCase,
    private val getCurrentUserUseCase: com.ulises.usecase.session.GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    data class UiState(
        val createdText: String = "",
        val comments: String = "",
        val isLoading: Boolean = false,
        val isReadyToSend: Boolean = false,
        val kidName: String = "Renata",
        val addComplete: Boolean = false,
        val isDateDialogVisible: Boolean = false,
        val selectedDate: LocalDate = LocalDate.now()
    )

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
                _uiState.update { it.copy(comments = text) }
            }
        }
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
                    kidName = _uiState.value.kidName,
                    comments = _uiState.value.comments,
                    createdById = user?.id ?: "Unknown"
                )
                addScheduledEventUseCase(scheduleEvent)
                scheduleEvent
            }.onSuccess { event ->
                Timber.d("Event created: $event")
                _uiState.update { it.copy(isLoading = false, addComplete = true) }
            }.onFailure {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun validateInputsFilled() {
        val isReady = _uiState.value.createdText.isNotBlank()
        _uiState.update { it.copy(isReadyToSend = isReady) }
    }
}

sealed interface TextFieldType {
    data object CreatedBy : TextFieldType
    data object Comment : TextFieldType
}