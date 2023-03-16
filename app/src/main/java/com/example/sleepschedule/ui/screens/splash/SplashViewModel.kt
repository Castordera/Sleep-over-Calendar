package com.example.sleepschedule.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sleepschedule.di.AppDispatchers
import com.ulises.usecases.session.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dispatchers: AppDispatchers,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    data class UiState(
        val loading: Boolean = true,
        val userFound: Boolean? = null,
        val error: String? = null
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        viewModelScope.launch(dispatchers.main) {
            runCatching {
                getCurrentUserUseCase()
            }.fold(
                onSuccess = { user ->
                    _uiState.update {
                        it.copy(userFound = user != null)
                    }
                },
                onFailure = {
                    _uiState.update { it.copy(error = it.error) }
                }
            )
        }
    }
}