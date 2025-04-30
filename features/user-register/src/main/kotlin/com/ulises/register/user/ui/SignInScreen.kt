package com.ulises.register.user.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.components.AppTextField
import com.ulises.components.toolbar.TopBar
import com.ulises.register.user.R
import com.ulises.register.user.models.Intents
import com.ulises.register.user.models.TextType
import com.ulises.register.user.models.UiState
import com.ulises.theme.SleepScheduleTheme

@Composable
internal fun SignInScreen(
    uiState: UiState,
    navigateBack: () -> Unit = {},
    getTextField: (TextType) -> String = { _ -> "" },
    onHandleIntent: (Intents) -> Unit = {},
) {
    val snackBarHostState = remember { SnackbarHostState() }

    if (uiState.error.isNotBlank()) {
        LaunchedEffect(uiState.error) {
            snackBarHostState.showSnackbar(uiState.error)
            onHandleIntent(Intents.DismissError)
        }
    }

    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.signin_top_bar)) { navigateBack() }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.label_signin_title),
                fontSize = 20.sp
            )
            AppTextField(
                text = getTextField(TextType.Name),
                label = stringResource(id = R.string.filed_name_or_nickname),
                enabled = !uiState.isLoading,
                onTextChange = { onHandleIntent(Intents.UpdateText(TextType.Name, it)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                message = stringResource(id = R.string.message_name_or_nickname)
            )
            AppTextField(
                text = getTextField(TextType.Email),
                label = stringResource(id = R.string.field_email),
                enabled = !uiState.isLoading,
                onTextChange = { onHandleIntent(Intents.UpdateText(TextType.Email, it)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
            )
            AppTextField(
                text = getTextField(TextType.Password),
                label = stringResource(id = R.string.field_password),
                message = stringResource(id = R.string.field_label_min_characters, "6"),
                enabled = !uiState.isLoading,
                passwordVisible = false,
                onTextChange = { onHandleIntent(Intents.UpdateText(TextType.Password, it)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    autoCorrectEnabled = false
                ),
            )
            AppTextField(
                text = getTextField(TextType.RePassword),
                label = stringResource(id = R.string.field_re_password),
                message = stringResource(id = R.string.field_label_min_characters, "6"),
                enabled = !uiState.isLoading,
                passwordVisible = false,
                onTextChange = { onHandleIntent(Intents.UpdateText(TextType.RePassword, it)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    autoCorrectEnabled = false
                ),
            )
            Button(
                onClick = { onHandleIntent(Intents.RegisterUser) },
                enabled = uiState.isEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.button_signin))
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PrevSignInScreen() {
    SleepScheduleTheme {
        SignInScreen(
            uiState = UiState(),
        )
    }
}