package com.ulises.register.user.models

data class UiState(
    val isLoading: Boolean = false,
    val isEnabled: Boolean = false,
    val nameNickname: String = "",
    val email: String = "",
    val password: String = "",
    val rePassword: String = "",
    val error: String = "",
    val navigateToHome: Boolean = false
)