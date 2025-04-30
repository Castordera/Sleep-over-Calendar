package com.ulises.login.user.model

import com.ulises.navigation.Screens

data class UiState(
    val isLoading: Boolean = false,
    val navigateTo: Screens? = null,
    val error: String = ""
)