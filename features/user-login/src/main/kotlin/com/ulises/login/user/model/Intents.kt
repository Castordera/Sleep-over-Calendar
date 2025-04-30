package com.ulises.login.user.model

sealed interface Intents {
    data class UpdateTextField(val type: TextFieldType, val value: String) : Intents
    data object LoginClicked : Intents
    data object SignInClicked : Intents
}