package com.ulises.register.user.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.dispatcher_core.ScheduleDispatchers
import com.ulises.register.user.models.Intents
import com.ulises.register.user.models.TextType
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
    //  Text Fields state
    private var nickname by mutableStateOf("")
    private var email by mutableStateOf("")
    private var password by mutableStateOf("")
    private var verifyPassword by mutableStateOf("")
    //

    fun onHandleIntent(intent: Intents) {
        when (intent) {
            Intents.RegisterUser -> onSignInButtonClick()
            Intents.DismissError -> onErrorShown()
            is Intents.UpdateText -> onTextChange(intent.type, intent.text)
        }
    }

    fun getTextField(type: TextType): String {
        return when (type) {
            TextType.Email -> email
            TextType.Name -> nickname
            TextType.Password -> password
            TextType.RePassword -> verifyPassword
        }
    }

    private fun onTextChange(type: TextType, text: String) {
        when (type) {
            is TextType.Email -> email = text
            is TextType.Password -> password = text
            is TextType.RePassword -> verifyPassword = text
            is TextType.Name -> nickname = text
        }
        validateInputs()
    }

    private fun onErrorShown() {
        _uiState.update { it.copy(error = "") }
    }

    private fun onSignInButtonClick() {
        viewModelScope.launch(dispatchers.main) {
            runCatching {
                _uiState.update { it.copy(isLoading = true) }
                val user = registerUserUseCase(email, password)
                createUserUseCase(requireNotNull(user?.copy(name = nickname)))
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

    //Todo(Fix this)
    private fun validateInputs() {
        viewModelScope.launch {
//            val validationList = mutableListOf<Boolean>()
//            with(_uiState.value) {
//                validationList.add(nameNickname.isNotBlank())
//                validationList.add(email.isNotBlank())
//                validationList.add(password.length >= 6)
//                validationList.add(rePassword.length >= 6)
//                validationList.add(password == rePassword)
//                _uiState.update { it.copy(isEnabled = validationList.all { value -> value }) }
//            }
        }
    }
}