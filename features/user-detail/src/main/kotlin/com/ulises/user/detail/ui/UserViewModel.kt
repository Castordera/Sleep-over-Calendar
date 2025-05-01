package com.ulises.user.detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.dispatcher_core.ScheduleDispatchers
import com.ulises.session.UserSessionManager
import com.ulises.usecase.session.CloseSessionUseCase
import com.ulises.user.detail.models.Intents
import com.ulises.user.detail.models.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val dispatchers: ScheduleDispatchers,
    private val closeSessionUseCase: CloseSessionUseCase,
    private val userSessionManager: UserSessionManager,
) : ViewModel() {

    init {
        getUser()
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onHandleIntent(intent: Intents) {
        when (intent) {
            Intents.LogoutClicked -> onLogoutClicked()
        }
    }

    private fun getUser() {
        viewModelScope.launch(dispatchers.main) {
            _uiState.update { it.copy(isLoading = true) }
            userSessionManager.userSessionState
                .collect { user ->
                    _uiState.update { it.copy(isLoading = false, user = user) }
                }
        }
    }

    private fun onLogoutClicked() {
        viewModelScope.launch(dispatchers.main) {
            closeSessionUseCase()
            userSessionManager.updateUserSessionState(null)
            _uiState.update { it.copy(loggedOutAction = true) }
        }
    }
}