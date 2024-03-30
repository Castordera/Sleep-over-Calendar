package com.ulises.features.splash.models

data class UiState(
    val isLoading: Boolean = true,
    val userFound: Boolean? = null,
    val error: String? = null
)