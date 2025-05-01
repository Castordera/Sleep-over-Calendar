package com.ulises.features.splash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.dispatcher_core.ScheduleDispatchers
import com.ulises.features.splash.models.UiState
import com.ulises.session.UserSessionManager
import com.ulises.usecase.user.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dispatchers: ScheduleDispatchers,
    private val userSessionManager: UserSessionManager,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        viewModelScope.launch(dispatchers.main) {
            runCatching {
                getCurrentUserUseCase()?.also(userSessionManager::updateUserSessionState)
            }.onFailure { error ->
                Timber.e(error, "Error getting user data")
                _uiState.update { it.copy(error = error.message, isLoading = false) }
            }.onSuccess { user ->
                Timber.d("User found with data: $user")
                _uiState.update { it.copy(userFound = user != null, isLoading = false) }
            }
        }
    }
}