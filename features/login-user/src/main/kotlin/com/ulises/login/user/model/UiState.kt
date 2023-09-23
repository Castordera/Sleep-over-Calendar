package com.ulises.login.user.model

import com.ulises.navigation.Screens

data class UiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val navigateTo: Screens? = null,
    val error: String = ""
)