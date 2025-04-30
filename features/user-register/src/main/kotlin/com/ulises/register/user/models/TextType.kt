package com.ulises.register.user.models

sealed interface TextType {
    data object Email : TextType
    data object Password : TextType
    data object RePassword : TextType
    data object Name : TextType
}