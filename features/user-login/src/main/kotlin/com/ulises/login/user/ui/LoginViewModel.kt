package com.ulises.login.user.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulises.dispatcher_core.ScheduleDispatchers
import com.ulises.login.user.model.Intents
import com.ulises.login.user.model.TextFieldType
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
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
    //
    private var email by mutableStateOf("")
    private var password by mutableStateOf("")
    //

    fun onHandleIntent(intent: Intents) {
        when (intent) {
            Intents.LoginClicked -> onLoginClick()
            Intents.SignInClicked -> onSignInClicked()
            is Intents.UpdateTextField -> {
                when (intent.type) {
                    TextFieldType.Email -> email = intent.value
                    TextFieldType.Password -> password = intent.value
                }
            }
        }
    }

    private fun onLoginClick() {
        viewModelScope.launch(dispatchers.main) {
            runCatching {
                loginUseCase(email, password)
            }.fold(
                onSuccess = { user ->
                    Timber.d("Login Success for: $user")
                    _uiState.update { it.copy(navigateTo = Screens.Home) }
                },
                onFailure = { error ->
                    Timber.e("Login error ${error.message}", error)
                    _uiState.update { it.copy(error = error.message.orEmpty()) }
                }
            )
        }
    }

    fun getTextFieldValue(textFieldType: TextFieldType): String {
        return when (textFieldType) {
            TextFieldType.Email -> email
            TextFieldType.Password -> password
        }
    }

    fun onErrorShowed() {
        _uiState.update { it.copy(error = "") }
    }

    private fun onSignInClicked() {
        _uiState.update { it.copy(navigateTo = Screens.SignIn) }
    }

    fun onNavigatedToRegister() {
        _uiState.update { it.copy(navigateTo = null) }
    }
}