package com.example.sleepschedule.ui.screens.login

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sleepschedule.R
import com.example.sleepschedule.ui.components.AppTextField
import com.example.sleepschedule.ui.components.TextType
import com.example.sleepschedule.ui.navigation.Screens
import com.example.sleepschedule.ui.theme.SleepScheduleTheme

@Composable
fun LoginRoute(
    loginViewModel: LoginViewModel = viewModel(),
    navigateTo: (Screens) -> Unit
) {
    val uiState by loginViewModel.uiState.collectAsState()

    if (uiState.navigateTo != null) {
        LaunchedEffect(key1 = Unit) {
            navigateTo(uiState.navigateTo!!)
            loginViewModel.onNavigatedToRegister()
        }
    }

    LoginScreen(
        uiState = uiState,
        onTextChange = loginViewModel::onTextChange,
        onLoginClick = loginViewModel::onLoginClick,
        onSignInClick = loginViewModel::onSignInClicked
    )
}

@Composable
fun LoginScreen(
    uiState: LoginViewModel.UiState,
    onTextChange: (type: TextType, text: String) -> Unit,
    onLoginClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    Scaffold { padding ->
        Surface(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
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
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
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