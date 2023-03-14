package com.example.sleepschedule.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sleepschedule.common.TimeHelper
import com.example.sleepschedule.common.TimeHelper.toISODate
import com.example.sleepschedule.di.AppDispatchers
import com.ulises.usecases.AddScheduledEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import outcomes.OutcomeScheduledEvent
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ScheduleDateDetailViewModel @Inject constructor(
    private val dispatchers: AppDispatchers,
    private val addScheduledEventUseCase: AddScheduledEventUseCase
) : ViewModel() {

    private var selectedTime: String = ""
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    data class UiState(
        val createdText: String = "",
        val dateText: String = "",
        val comments: String = "",
        val isLoading: Boolean = false,
        val isReadyToSend: Boolean = false,
        val kidName: String = "Renata",
        val addComplete: Boolean = false
    )

    init {
        calculateInitialDate()
    }

    private fun calculateInitialDate() {
        viewModelScope.launch {
            selectedTime = LocalDate.now().toISODate()
            _uiState.update { it.copy(dateText = TimeHelper.convertToHumanReadable(selectedTime)) }
        }
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

    fun onDateSelected(year: Int, month: Int, day: Int) {
        viewModelScope.launch {
            selectedTime = TimeHelper.convertRawToFormattedString(year, month + 1, day)
            _uiState.update { it.copy(dateText = TimeHelper.convertToHumanReadable(selectedTime)) }
        }
    }

    fun onAddSchedule() {
        viewModelScope.launch(dispatchers.main) {
            _uiState.update { it.copy(isLoading = true) }
            val scheduleEvent = OutcomeScheduledEvent(
                id = Date().time.toString(),
                date = selectedTime,
                createdBy = _uiState.value.createdText.trim(),
                createdOn = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                rating = 0,
                kidName = _uiState.value.kidName,
                comments = _uiState.value.comments
            )
            runCatching {
                addScheduledEventUseCase(scheduleEvent)
            }.onSuccess {
                _uiState.update { it.copy(isLoading = false, addComplete = true) }
            }.onFailure {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun validateInputsFilled() {
        val isReady = selectedTime.isNotBlank() && _uiState.value.createdText.isNotBlank()
        _uiState.update { it.copy(isReadyToSend = isReady) }
    }
}

sealed interface TextFieldType {
    object CreatedBy : TextFieldType
    object Comment: TextFieldType
}