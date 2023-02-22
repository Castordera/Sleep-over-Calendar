package com.example.sleepschedule.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sleepschedule.common.TimeHelper
import com.example.sleepschedule.common.getDateFromRaw
import com.example.sleepschedule.di.AppDispatchers
import com.ulises.usecases.AddScheduledEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import outcomes.OutcomeScheduledEvent
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ScheduleDateDetailViewModel @Inject constructor(
    private val dispatchers: AppDispatchers,
    private val timeHelper: TimeHelper,
    private val addScheduledEventUseCase: AddScheduledEventUseCase
): ViewModel() {

    private var timeSelectedMillis: Long = 0L
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    data class UiState(
        val createdText: String = "",
        val dateText: String = "",
        val isLoading: Boolean = false
    )

    fun onUpdateTextField(type: TextFieldType, text: String) {
        when (type) {
            is TextFieldType.CreatedBy -> {
                _uiState.update { it.copy(createdText = text) }
            }
        }
    }

    fun onDateSelected(year: Int, month: Int, day: Int) {
        viewModelScope.launch(dispatchers.main) {
            timeSelectedMillis = Calendar.getInstance().getDateFromRaw(year, month, day)
            val time = timeHelper.convertToLocalDateFromMillis(timeSelectedMillis)
            _uiState.update { it.copy(dateText = time) }
        }
    }

    fun onAddSchedule() {
        viewModelScope.launch(dispatchers.main) {
            _uiState.update { it.copy(isLoading = true) }
            val scheduleEvent = OutcomeScheduledEvent(
                id = Date().time.toString(),
                date = timeSelectedMillis,
                createdBy = _uiState.value.createdText,
                createdOn = LocalDate.now().toEpochDay(),
                rating = 0
            )
            runCatching {
                addScheduledEventUseCase(scheduleEvent)
            }.onSuccess {
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}

sealed interface TextFieldType {
    object CreatedBy: TextFieldType
}