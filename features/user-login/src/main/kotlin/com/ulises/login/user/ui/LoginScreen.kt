package com.ulises.login.user.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulises.login.user.ui.content.LoginScreenContent
import com.ulises.navigation.Screens

@Composable
fun LoginScreen(
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

    LoginScreenContent(
        uiState = uiState,
        onHandleIntent = loginViewModel::onHandleIntent,
    )
}