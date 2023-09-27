package com.ulises.register.user.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.components.TextType
import com.ulises.dispatcher_core.ScheduleDispatchers
import com.ulises.register.user.models.UiState
import com.ulises.usecase.session.RegisterUserUseCase
import com.ulises.usecase.user.CreateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val dispatchers: ScheduleDispatchers,
    private val registerUserUseCase: RegisterUserUseCase,
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onTextChange(type: TextType, text: String) {
        when (type) {
            is TextType.Email -> _uiState.update { it.copy(email = text) }
            is TextType.Password -> _uiState.update { it.copy(password = text) }
            is TextType.RePassword -> _uiState.update { it.copy(rePassword = text) }
            is TextType.Name -> _uiState.update { it.copy(nameNickname = text) }
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
                val user = registerUserUseCase(uiState.value.email, uiState.value.password)
                createUserUseCase(requireNotNull(user?.copy(name = uiState.value.nameNickname)))
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
                validationList.add(nameNickname.isNotBlank())
                validationList.add(email.isNotBlank())
                validationList.add(password.length >= 6)
                validationList.add(rePassword.length >= 6)
                validationList.add(password == rePassword)
                _uiState.update { it.copy(isEnabled = validationList.all { value -> value }) }
            }
        }
    }
}