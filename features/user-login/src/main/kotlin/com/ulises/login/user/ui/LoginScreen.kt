package com.ulises.login.user.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulises.login.user.model.Action
import com.ulises.login.user.ui.content.LoginScreenContent
import com.ulises.navigation.Screens

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigateToSignIn: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateTo) {
        when (uiState.navigateTo) {
            Screens.Home -> navigateToHome()
            else -> Unit
        }
    }

    LoginScreenContent(
        uiState = uiState,
        onHandleIntent = {
            when (it) {
                Action.SignInClicked -> navigateToSignIn()
                else -> loginViewModel.onHandleIntent(it)
            }
        },
    )
}