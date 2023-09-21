package com.example.sleepschedule.ui.screens.user

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sleepschedule.R
import com.ulises.theme.SleepScheduleTheme

@Composable
fun UserRoute(
    userViewModel: UserViewModel = hiltViewModel(),
    onLoggedOut: () -> Unit
) {
    val uiState by userViewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.loggedOutAction) {
        LaunchedEffect(Unit) {
            onLoggedOut()
        }
    }

    UserScreen(
        uiState = uiState,
        onLogoutClicked = userViewModel::onLogoutClicked
    )
}

@Composable
fun UserScreen(
    uiState: UserViewModel.UiState,
    onLogoutClicked: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            OutlinedButton(
                enabled = !uiState.isLoading,
                border = BorderStroke(0.dp, Color.Transparent),
                onClick = onLogoutClicked
            ) {
                Text(text = stringResource(id = R.string.button_close_session))
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevUserScreen() {
    SleepScheduleTheme {
        UserScreen(
            uiState = UserViewModel.UiState()
        )
    }
}