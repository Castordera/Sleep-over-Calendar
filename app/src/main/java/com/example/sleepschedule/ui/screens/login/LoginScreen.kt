package com.example.sleepschedule.ui.screens.login

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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sleepschedule.R
import com.example.sleepschedule.ui.components.AppTextField
import com.example.sleepschedule.ui.components.TextType
import com.example.sleepschedule.ui.navigation.Screens
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
    uiState: LoginViewModel.UiState,
    onTextChange: (type: TextType, text: String) -> Unit,
    onLoginClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
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
            AppTextField(
                text = uiState.email,
                label = stringResource(id = R.string.field_email),
                enabled = !uiState.isLoading,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                onTextChange = { onTextChange(TextType.Email, it) }
            )
            AppTextField(
                text = uiState.password,
                label = stringResource(id = R.string.field_password),
                enabled = !uiState.isLoading,
                passwordVisible = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    autoCorrect = false
                ),
                onTextChange = { onTextChange(TextType.Password, it) }
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
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevLoginScreen() {
    SleepScheduleTheme {
        LoginScreen(
            uiState = LoginViewModel.UiState(),
            onTextChange = { _, _ -> },
            onLoginClick = {},
            onSignInClick = {}
        )
    }
}