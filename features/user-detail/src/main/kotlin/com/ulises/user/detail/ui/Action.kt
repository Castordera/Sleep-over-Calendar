package com.ulises.user.detail.ui

sealed interface Action {
    data object BackPressed : Action
    data object Logout : Action
}