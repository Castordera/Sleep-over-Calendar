package com.example.sleepschedule.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sleepschedule.ui.components.TextType
import com.example.sleepschedule.ui.navigation.Screens
import com.ulises.dispatcher_core.ScheduleDispatchers
import com.ulises.usecases.session.LoginUseCase
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
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        val navigateTo: Screens? = null,
        val error: String = ""
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onTextChange(type: TextType, text: String) {
        when (type) {
            is TextType.Email -> {
                _uiState.update { it.copy(email = text) }
            }
            is TextType.Password -> {
                _uiState.update { it.copy(password = text) }
            }
            else -> Timber.d("Option not handled: $type")
        }
    }

    fun onLoginClick() {
        viewModelScope.launch(dispatchers.main) {
            runCatching {
                loginUseCase(uiState.value.email, uiState.value.password)
            }.fold(
                onSuccess = { user ->
                    Timber.d("Login Success for: $user")
                    _uiState.update { it.copy(navigateTo = Screens.Home) }
                },
                onFailure = { error ->
                    Timber.d("Login error ${error.message}", error)
                    _uiState.update { it.copy(error = error.message.orEmpty()) }
                }
            )
        }
    }

    fun onErrorShowed() {
        _uiState.update { it.copy(error = "") }
    }

    fun onSignInClicked() {
        _uiState.update { it.copy(navigateTo = Screens.SignIn) }
    }

    fun onNavigatedToRegister() {
        _uiState.update { it.copy(navigateTo = null) }
    }
}