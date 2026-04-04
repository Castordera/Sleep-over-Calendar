package com.ulises.login.user.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.dispatcher_core.ScheduleDispatchers
import com.ulises.login.user.model.Action
import com.ulises.login.user.model.UiState
import com.ulises.navigation.Screens
import com.ulises.usecase.session.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dispatchers: ScheduleDispatchers,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onHandleIntent(action: Action) {
        when (action) {
            is Action.LoginClicked -> onLoginClick(action.email, action.password)
            else -> Timber.d("Action not handled: $action")
        }
    }

    private fun onLoginClick(email: String, password: String) {
        viewModelScope.launch(dispatchers.main) {
            runCatching {
                loginUseCase(email, password)
            }.fold(
                onSuccess = { user ->
                    Timber.d("Login Success for: $user")
                    _uiState.update { it.copy(navigateTo = Screens.Home) }
                },
                onFailure = { error ->
                    Timber.e(error, "Login error ${error.message}")
                    _uiState.update { it.copy(error = error.message ?: "$error") }
                }
            )
        }
    }
}