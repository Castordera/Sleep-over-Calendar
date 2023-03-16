package com.example.sleepschedule.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp

@Composable
fun AppTextField(
    text: String,
    label: String,
    message: String? = null,
    enabled: Boolean,
    passwordVisible: Boolean = true,
    keyboardOptions: KeyboardOptions,
    onTextChange: (text: String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            enabled = enabled,
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = keyboardOptions,
            label = { Text(text = label) },
            modifier = Modifier.fillMaxWidth()
        )
        message?.also {
            Text(
                text = it,
                fontSize = 12.sp
            )
        }
    }
}

sealed interface TextType {
    object Email: TextType
    object Password: TextType
    object RePassword: TextType
}