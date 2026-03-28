package com.ulises.user.detail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulises.user.detail.ui.contents.UserDetailContent

@Composable
fun UserRoute(
    userViewModel: UserViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onLoggedOut: () -> Unit,
) {
    val uiState by userViewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.loggedOutAction) {
        LaunchedEffect(Unit) {
            onLoggedOut()
        }
    }

    UserDetailContent(
        uiState = uiState,
        onHandleAction = {
            when (it) {
                Action.BackPressed -> onBackClicked()
                else -> userViewModel.onHandleAction(it)
            }
        }
    )
}