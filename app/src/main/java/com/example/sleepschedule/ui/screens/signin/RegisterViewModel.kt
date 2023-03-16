package com.example.sleepschedule.ui.screens.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sleepschedule.di.AppDispatchers
import com.example.sleepschedule.ui.components.TextType
import com.ulises.usecases.session.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val dispatchers: AppDispatchers,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = false,
        val isEnabled: Boolean = false,
        val email: String = "",
        val password: String = "",
        val rePassword: String = "",
        val error: String = "",
        val navigateToHome: Boolean = false
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onTextChange(type: TextType, text: String) {
        when (type) {
            is TextType.Email -> _uiState.update { it.copy(email = text) }
            is TextType.Password -> _uiState.update { it.copy(password = text) }
            is TextType.RePassword -> _uiState.update { it.copy(rePassword = text) }
        }
        validateInputs()
    }

    fun onErrorShown() {
        viewModelScope.launch(dispatchers.main) {
            _uiState.update { it.copy(error = "") }
        }
    }

    fun onSignInButtonClick() {
        viewModelScope.launch(dispatchers.main) {
            runCatching {
                _uiState.update { it.copy(isLoading = true) }
                registerUserUseCase(uiState.value.email, uiState.value.password)
            }.fold(
                onSuccess = { user ->
                    Timber.d("User created : $user")
                    _uiState.update { it.copy(navigateToHome = true) }
                },
                onFailure = { error ->
                    Timber.e("Error creating the user", error)
                    _uiState.update {
                        it.copy(
                            error = error.message ?: "Default",
                            isLoading = false
                        )
                    }
                }
            )
        }
    }

    private fun validateInputs() {
        viewModelScope.launch {
            val validationList = mutableListOf<Boolean>()
            with(_uiState.value) {
                validationList.add(email.isNotBlank())
                validationList.add(password.length >= 6)
                validationList.add(rePassword.length >= 6)
                validationList.add(password == rePassword)
                _uiState.update { it.copy(isEnabled = validationList.all { value -> value }) }
            }
        }
    }
}