package com.example.sleepschedule.ui.screens.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.dispatcher_core.ScheduleDispatchers
import com.ulises.usecases.session.CloseSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val dispatchers: ScheduleDispatchers,
    private val closeSessionUseCase: CloseSessionUseCase
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = false,
        val loggedOutAction: Boolean = false
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onLogoutClicked() {
        viewModelScope.launch(dispatchers.main) {
            closeSessionUseCase()
            _uiState.update { it.copy(loggedOutAction = true) }
        }
    }
}