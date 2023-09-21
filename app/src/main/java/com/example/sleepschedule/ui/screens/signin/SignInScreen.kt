package com.example.sleepschedule.ui.screens.signin

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sleepschedule.R
import com.example.sleepschedule.ui.components.AppTextField
import com.example.sleepschedule.ui.components.TextType
import com.example.sleepschedule.ui.components.TopBar
import com.ulises.theme.SleepScheduleTheme

@Composable
fun SignInRoute(
    registerViewModel: RegisterViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by registerViewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    if (uiState.error.isNotBlank()) {
        LaunchedEffect(uiState.error) {
            snackBarHostState.showSnackbar(uiState.error)
            registerViewModel.onErrorShown()
        }
    }

    if (uiState.navigateToHome) {
        LaunchedEffect(Unit) {
            navigateToHome()
        }
    }

    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.signin_top_bar)) { navigateBack() }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        SignInScreen(
            modifier = Modifier.padding(it),
            uiState = uiState,
            onTextChange = registerViewModel::onTextChange,
            onSignInClick = registerViewModel::onSignInButtonClick
        )
    }
}

@Composable
private fun SignInScreen(
    modifier: Modifier = Modifier,
    uiState: RegisterViewModel.UiState,
    onTextChange: (type: TextType, text: String) -> Unit,
    onSignInClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.label_signin_title),
                fontSize = 20.sp
            )
            AppTextField(
                text = uiState.nameNickname,
                label = stringResource(id = R.string.filed_name_or_nickname),
                enabled = !uiState.isLoading,
                onTextChange = { onTextChange(TextType.Name, it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                message = stringResource(id = R.string.message_name_or_nickname)
            )
            AppTextField(
                text = uiState.email,
                label = stringResource(id = R.string.field_email),
                enabled = !uiState.isLoading,
                onTextChange = { onTextChange(TextType.Email, it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
            )
            AppTextField(
                text = uiState.password,
                label = stringResource(id = R.string.field_password),
                message = stringResource(id = R.string.field_label_min_characters, "6"),
                enabled = !uiState.isLoading,
                passwordVisible = false,
                onTextChange = { onTextChange(TextType.Password, it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    autoCorrect = false
                ),
            )
            AppTextField(
                text = uiState.rePassword,
                label = stringResource(id = R.string.field_re_password),
                message = stringResource(id = R.string.field_label_min_characters, "6"),
                enabled = !uiState.isLoading,
                passwordVisible = false,
                onTextChange = { onTextChange(TextType.RePassword, it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    autoCorrect = false
                ),
            )
            Button(
                onClick = onSignInClick,
                enabled = uiState.isEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.button_signin))
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrevSignInScreen() {
    SleepScheduleTheme {
        SignInScreen(
            uiState = RegisterViewModel.UiState(),
            onTextChange = { _, _ -> },
            onSignInClick = {}
        )
    }
}