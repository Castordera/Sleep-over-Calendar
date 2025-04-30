package com.ulises.register.user.models

data class UiState(
    val isLoading: Boolean = false,
    val isEnabled: Boolean = false,
    val error: String = "",
    val navigateToHome: Boolean = false
)