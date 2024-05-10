package com.ulises.features.events.list.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ScheduleListRoute(
    viewModel: MainScheduleViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState,
    onNavigateToAdd: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ScheduleListScreen(
        uiState = uiState,
        snackBarHostState = snackBarHostState,
        onNavigateToAdd = onNavigateToAdd,
        onClickItem = viewModel::onClickItemForRotation,
        onDialogChangeVisibility = viewModel::onDialogCloseVisibilityChange,
        onUpdateRating = viewModel::onUpdateRating,
        onDeleteEvent = viewModel::onDeleteScheduleEvent,
        onErrorDisplayed = viewModel::onErrorDisplayed,
    )
}