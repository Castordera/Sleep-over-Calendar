package com.ulises.user.detail.models

import models.User

data class UiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val loggedOutAction: Boolean = false,
)