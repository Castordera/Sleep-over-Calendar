package com.ulises.login.user.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulises.login.user.R
import com.ulises.login.user.model.Intents
import com.ulises.login.user.model.TextFieldType
import com.ulises.login.user.model.UiState
import com.ulises.navigation.Screens
import com.ulises.theme.SleepScheduleTheme

@Composable
fun LoginRoute(
    loginViewModel: LoginViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState,
    navigateTo: (Screens) -> Unit
) {
    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.error.isNotBlank()) {
        LaunchedEffect(uiState.error) {
            snackBarHostState.showSnackbar(uiState.error)
            loginViewModel.onErrorShowed()
        }
    }

    if (uiState.navigateTo != null) {
        LaunchedEffect(Unit) {
            navigateTo(uiState.navigateTo!!)
            loginViewModel.onNavigatedToRegister()
        }
    }

    LoginScreen(
        uiState = uiState,
        snackBarHostState = snackBarHostState,
        onHandleIntent = loginViewModel::onHandleIntent,
        getTextFieldValue = loginViewModel::getTextFieldValue,
    )
}

@Composable
private fun LoginScreen(
    uiState: UiState,
    snackBarHostState: SnackbarHostState,
    onHandleIntent: (Intents) -> Unit = {},
    getTextFieldValue: (TextFieldType) -> String = { _ -> "" },
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val isLoginEnabled by remember {
        derivedStateOf {
            getTextFieldValue(TextFieldType.Email).isNotBlank()
                    && getTextFieldValue(TextFieldType.Password).isNotBlank()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .imePadding()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(320.dp)
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text(stringResource(id = R.string.field_email)) },
                value = getTextFieldValue(TextFieldType.Email),
                onValueChange = {
                    onHandleIntent(Intents.UpdateTextField(TextFieldType.Email, it))
                },
                enabled = !uiState.isLoading,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text(stringResource(id = R.string.field_password)) },
                value = getTextFieldValue(TextFieldType.Password),
                onValueChange = {
                    onHandleIntent(Intents.UpdateTextField(TextFieldType.Password, it))
                },
                enabled = !uiState.isLoading,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = if (isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        if (isPasswordVisible) {
                            Icon(
                                painterResource(id = R.drawable.visibility_24),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                painterResource(id = R.drawable.visibility_off_24),
                                contentDescription = null
                            )
                        }
                    }
                }
            )
            Button(
                onClick = { onHandleIntent(Intents.LoginClicked) },
                enabled = !uiState.isLoading && isLoginEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.button_login))
            }
            OutlinedButton(
                onClick = { onHandleIntent(Intents.SignInClicked) },
                border = BorderStroke(0.dp, Color.Transparent)
            ) {
                Text(text = stringResource(id = R.string.button_signin))
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PrevLoginScreen() {
    SleepScheduleTheme {
        LoginScreen(
            uiState = UiState(),
            snackBarHostState = SnackbarHostState(),
        )
    }
}