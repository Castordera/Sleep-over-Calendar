package com.ulises.login.user.model

sealed interface Action {
    data class LoginClicked(val email: String, val password: String) : Action
    data object SignInClicked : Action
}