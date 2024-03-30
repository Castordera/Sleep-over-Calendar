package com.ulises.login.user.ui

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulises.components.TextType
import com.ulises.login.user.R
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

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        LoginScreen(
            modifier = Modifier.padding(padding),
            uiState = uiState,
            onTextChange = loginViewModel::onTextChange,
            onLoginClick = loginViewModel::onLoginClick,
            onSignInClick = loginViewModel::onSignInClicked
        )
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onTextChange: (type: TextType, text: String) -> Unit,
    onLoginClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
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
            value = uiState.email,
            onValueChange = { onTextChange(TextType.Email, it) },
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
            value = uiState.password,
            onValueChange = { onTextChange(TextType.Password, it) },
            enabled = !uiState.isLoading,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                autoCorrect = false,
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
                        Icon(painterResource(id = R.drawable.visibility_24), contentDescription = null)
                    } else {
                        Icon(painterResource(id = R.drawable.visibility_off_24), contentDescription = null)
                    }
                }
            }
        )
        Button(
            onClick = onLoginClick,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.button_login))
        }
        OutlinedButton(
            onClick = onSignInClick,
            border = BorderStroke(0.dp, Color.Transparent)
        ) {
            Text(text = stringResource(id = R.string.button_signin))
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevLoginScreen() {
    SleepScheduleTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LoginScreen(
                uiState = UiState(),
                onTextChange = { _, _ -> },
                onLoginClick = {},
                onSignInClick = {}
            )
        }

    }
}