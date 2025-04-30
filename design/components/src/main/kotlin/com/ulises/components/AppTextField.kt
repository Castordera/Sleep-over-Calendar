package com.ulises.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.theme.SleepScheduleTheme

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

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevTextField() {
    SleepScheduleTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                AppTextField(
                    text = "This is my text",
                    label = "Label",
                    enabled = true,
                    keyboardOptions = KeyboardOptions.Default,
                    onTextChange = {},
                )
                AppTextField(
                    text = "",
                    label = "Label",
                    message = "Message value",
                    enabled = true,
                    keyboardOptions = KeyboardOptions.Default,
                    onTextChange = {},
                )
            }
        }
    }
}