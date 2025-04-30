package com.ulises.features.events.list.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ScheduleListRoute(
    viewModel: MainScheduleViewModel = hiltViewModel(),
    onNavigateToAdd: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ScheduleListScreen(
        uiState = uiState,
        onNavigateToAdd = onNavigateToAdd,
        onHandleIntent = viewModel::onHandleIntent,
    )
}