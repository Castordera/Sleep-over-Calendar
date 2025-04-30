package com.ulises.login.user.model

sealed interface TextFieldType {
    data object Email : TextFieldType
    data object Password : TextFieldType
}