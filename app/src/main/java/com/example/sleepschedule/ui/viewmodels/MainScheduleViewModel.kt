package com.example.sleepschedule.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sleepschedule.di.AppDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScheduleViewModel @Inject constructor(
    private val dispatchers: AppDispatchers
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    data class UiState(
        val scheduleEvents: List<String>? = null
    )

    init {
        val scheduleItems = listOf("Ulises", "Ricardo", "Castorena", "Caldera")
        viewModelScope.launch(dispatchers.main) {
            _uiState.update { UiState(scheduleItems) }
        }
    }

    fun onAddScheduleDate() {

    }
}