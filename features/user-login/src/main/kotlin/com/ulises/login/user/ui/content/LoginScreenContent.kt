package com.ulises.login.user.ui.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Login
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.login.user.model.Action
import com.ulises.login.user.model.UiState
import com.ulises.login.user.ui.components.BedIllustration
import com.ulises.login.user.ui.components.NightTextField
import com.ulises.theme.DangerRed
import com.ulises.theme.LavenderDim
import com.ulises.theme.LavenderGlow
import com.ulises.theme.NightElevated
import com.ulises.theme.SleepScheduleTheme
import com.ulises.theme.TextMuted

@Composable
internal fun LoginScreenContent(
    uiState: UiState,
    onHandleIntent: (Action) -> Unit = {},
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            BedIllustration()
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Pijamadas",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 28.sp,
                    letterSpacing = (-0.3).sp,
                ),
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "El diario de tus noches favoritas",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(36.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
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
                    ),
                )
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
                            onHandleIntent(Action.LoginClicked(email, password))
                        }
                    ),
                    isFocused = true,
                )

                // ¿Olvidaste tu contraseña?
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    TextButton(
                        onClick = { },
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp),
                    ) {
                        Text(
                            text = "¿Olvidaste tu contraseña?",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = LavenderDim,
                                fontSize = 11.sp,
                            ),
                        )
                    }
                }

                // Error
                if (uiState.error.isNotBlank()) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = DangerRed.copy(alpha = 0.1f),
                        border = BorderStroke(1.dp, DangerRed.copy(alpha = 0.25f)),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Icon(
                                Icons.Rounded.ErrorOutline,
                                contentDescription = null,
                                tint = DangerRed,
                                modifier = Modifier.size(15.dp),
                            )
                            Text(
                                text = uiState.error,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = DangerRed,
                                    fontSize = 12.sp,
                                ),
                            )
                        }
                    }
                }
                Button(
                    onClick = { onHandleIntent(Action.LoginClicked(email, password)) },
                    enabled = email.isNotBlank() && password.isNotBlank() && !uiState.isLoading,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LavenderGlow,
                        contentColor = Color(0xFF1A0D4A),
                        disabledContainerColor = NightElevated,
                        disabledContentColor = TextMuted,
                    ),
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            color = Color(0xFF1A0D4A),
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(18.dp),
                        )
                    } else {
                        Icon(
                            Icons.Rounded.Login,
                            contentDescription = null,
                            modifier = Modifier.size(17.dp),
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Iniciar sesión",
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 14.sp),
                        )
                    }
                }

                // Divisor
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(vertical = 2.dp),
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = NightElevated,
                    )
                }

                // Registrarse
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "¿No tienes cuenta? ",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    )
                    TextButton(
                        onClick = { onHandleIntent(Action.SignInClicked) },
                    ) {
                        Text(
                            "Registrarme",
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = LavenderGlow,
                                fontSize = 12.sp,
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenContentPreview() {
    SleepScheduleTheme {
        LoginScreenContent(
            uiState = UiState(),
        )
    }
}