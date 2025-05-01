package com.ulises.user.detail.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulises.components.indicators.LoadingIndicator
import com.ulises.components.toolbar.TopBar
import com.ulises.theme.SleepScheduleTheme
import com.ulises.user.detail.R
import com.ulises.user.detail.models.Intents
import com.ulises.user.detail.models.UiState
import models.User

@Composable
fun UserRoute(
    userViewModel: UserViewModel = hiltViewModel(),
    onLoggedOut: () -> Unit,
) {
    val uiState by userViewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.loggedOutAction) {
        LaunchedEffect(Unit) {
            onLoggedOut()
        }
    }

    UserScreen(
        uiState = uiState,
        onHandleIntent = userViewModel::onHandleIntent,
    )
}

@Composable
private fun UserScreen(
    uiState: UiState,
    onHandleIntent: (Intents) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(title = "Perfil")
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
        ) {
            when {
                uiState.isLoading -> {
                    LoadingIndicator(isVisible = true, modifier = Modifier.size(30.dp))
                }

                uiState.user == null -> {
                    Text("Ups, un error")
                }

                else -> {
                    Text(uiState.user.name)
                    Text(uiState.user.email)
                    OutlinedButton(
                        border = BorderStroke(0.dp, Color.Transparent),
                        onClick = { onHandleIntent(Intents.LogoutClicked) },
                    ) {
                        Text(text = stringResource(id = R.string.button_close_session))
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PrevUserScreen() {
    SleepScheduleTheme {
        UserScreen(
            uiState = UiState(
                user = User(id = "12", email = "email", name = "This is my email")
            )
        )
    }
}