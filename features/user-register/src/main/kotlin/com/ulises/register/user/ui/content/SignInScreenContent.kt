package com.ulises.register.user.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ulises.components.NightTextField
import com.ulises.components.toolbar.AppTopBar
import com.ulises.register.user.models.Action
import com.ulises.register.user.models.UiState
import com.ulises.theme.LavenderGlow
import com.ulises.theme.SleepScheduleTheme
import com.ulises.theme.TextMuted

@Composable
internal fun SignInScreenContent(
    uiState: UiState,
    onHandleAction: (Action) -> Unit = {},
) {
    var nickname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var verifyPassword by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Registro",
                withIcon = false,
                onNavigateBack = {},
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .imePadding()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //  Name
            NightTextField(
                value = nickname,
                onValueChange = { nickname = it },
                placeholder = "Nombre o apodo",
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Person,
                        contentDescription = null,
                        tint = if (nickname.isNotEmpty()) LavenderGlow else TextMuted,
                        modifier = Modifier.size(17.dp),
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
            )
            // Email
            NightTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Correo electrónico",
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Email,
                        contentDescription = null,
                        tint = if (email.isNotEmpty()) LavenderGlow else TextMuted,
                        modifier = Modifier.size(17.dp),
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            // Password
            NightTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Contraseña",
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Lock,
                        contentDescription = null,
                        tint = if (password.isNotEmpty()) LavenderGlow else TextMuted,
                        modifier = Modifier.size(17.dp),
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier.size(32.dp),
                    ) {
                        Icon(
                            if (passwordVisible) Icons.Rounded.VisibilityOff
                            else Icons.Rounded.Visibility,
                            contentDescription = if (passwordVisible) "Ocultar" else "Mostrar",
                            tint = TextMuted,
                            modifier = Modifier.size(17.dp),
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
//                        onHandleIntent(
//                            com.ulises.login.user.model.Action.LoginClicked(
//                                email,
//                                password
//                            )
//                        )
                    }
                ),
            )
            //
            NightTextField(
                value = verifyPassword,
                onValueChange = { verifyPassword = it },
                placeholder = "Contraseña",
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Lock,
                        contentDescription = null,
                        tint = if (verifyPassword.isNotEmpty()) LavenderGlow else TextMuted,
                        modifier = Modifier.size(17.dp),
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier.size(32.dp),
                    ) {
                        Icon(
                            if (passwordVisible) Icons.Rounded.VisibilityOff
                            else Icons.Rounded.Visibility,
                            contentDescription = if (passwordVisible) "Ocultar" else "Mostrar",
                            tint = TextMuted,
                            modifier = Modifier.size(17.dp),
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
//                        onHandleIntent(
//                            com.ulises.login.user.model.Action.LoginClicked(
//                                email,
//                                password
//                            )
//                        )
                    }
                ),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSignInContent() {
    SleepScheduleTheme {
        SignInScreenContent(
            uiState = UiState(),
        )
    }
}
