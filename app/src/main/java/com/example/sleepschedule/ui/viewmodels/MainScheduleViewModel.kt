package com.example.sleepschedule.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sleepschedule.di.AppDispatchers
import com.ulises.usecases.GetAllScheduledEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import models.ScheduledEvent
import javax.inject.Inject

@HiltViewModel
class MainScheduleViewModel @Inject constructor(
    dispatchers: AppDispatchers,
    private val getAllScheduledEventsUseCase: GetAllScheduledEventsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    data class UiState(
        val scheduleEvents: List<ScheduledEvent>? = null,
        val isLoading: Boolean = false
    )

    init {
        viewModelScope.launch(dispatchers.io) {
            getAllScheduledEventsUseCase()
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch { _uiState.update { it.copy(isLoading = false) } }
                .collect { list ->
                    Log.d("MainScheduleViewModel", "Collected $list")
                    _uiState.update { it.copy(scheduleEvents = list, isLoading = false) }
                }
        }
    }
}