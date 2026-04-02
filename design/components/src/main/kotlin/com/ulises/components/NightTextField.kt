package com.ulises.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.theme.LavenderGlow
import com.ulises.theme.NightRim
import com.ulises.theme.NightSurface
import com.ulises.theme.TextMuted
import com.ulises.theme.TextPrimary

@Composable
fun NightTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isFocused: Boolean = false,
) {
    val borderColor = when {
        value.isNotEmpty() -> LavenderGlow.copy(alpha = 0.45f)
        isFocused -> LavenderGlow.copy(alpha = 0.55f)
        else -> NightRim
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                placeholder,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = TextMuted,
                    fontSize = 13.sp,
                ),
            )
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = LavenderGlow.copy(alpha = 0.55f),
            unfocusedBorderColor = borderColor,
            focusedContainerColor = NightSurface,
            unfocusedContainerColor = NightSurface,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            cursorColor = LavenderGlow,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
    )
}