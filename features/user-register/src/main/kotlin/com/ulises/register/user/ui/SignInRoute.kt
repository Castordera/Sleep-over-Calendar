package com.ulises.register.user.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SignInRoute(
    registerViewModel: RegisterViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateBack: () -> Unit,
) {
    val uiState by registerViewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.navigateToHome) {
        LaunchedEffect(Unit) {
            navigateToHome()
        }
    }

    SignInScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        getTextField = registerViewModel::getTextField,
        onHandleIntent = registerViewModel::onHandleIntent,
    )
}