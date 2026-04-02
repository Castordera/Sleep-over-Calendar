package com.ulises.register.user.models

sealed interface Action {
    data object RegisterUser : Action
    data object DismissError : Action
    data class UpdateText(val type: TextType, val text: String) : Action
}