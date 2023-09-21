package com.example.sleepschedule.ui.screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sleepschedule.ui.components.LoadingIndicator
import com.example.sleepschedule.ui.theme.SleepScheduleTheme

@Composable
fun SplashRoute(
    splashViewModel: SplashViewModel = hiltViewModel(),
    onUserLogAction: (Boolean) -> Unit
) {
    val uiState by splashViewModel.uiState.collectAsStateWithLifecycle()

    SplashScreen(
        uiState = uiState,
        onUserLogAction = onUserLogAction
    )
}

@Composable
fun SplashScreen(
    uiState: SplashViewModel.UiState,
    onUserLogAction: (Boolean) -> Unit = {}
) {
    LaunchedEffect(uiState.userFound) {
        uiState.userFound?.also(onUserLogAction)
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingIndicator(
                modifier = Modifier.size(300.dp)
            )
        }
    }
}

@Preview
@Composable
fun PrevSplashScreen() {
    SleepScheduleTheme {
        SplashScreen(
            uiState = SplashViewModel.UiState(),
        )
    }
}