package com.ulises.register.user.models

sealed interface Intents {
    data object RegisterUser : Intents
    data object DismissError : Intents
    data class UpdateText(val type: TextType, val text: String) : Intents
}