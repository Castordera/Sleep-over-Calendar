package com.ulises.user.detail.models

sealed interface Intents {
    data object LogoutClicked : Intents
}