package com.ulises.features.events.list.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulises.features.events.list.models.Actions
import com.ulises.features.events.list.ui.contents.ScheduleListV2Content
import models.ScheduledEvent

@Composable
fun ScheduleListScreen(
    viewModel: MainScheduleViewModel = hiltViewModel(),
    snackHost: SnackbarHostState,
    onNavigateToAdd: () -> Unit,
    onGoToDetail: (ScheduledEvent) -> Unit,
    onNavigateToProfile: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ScheduleListV2Content(
        uiState = uiState,
        snackHost = snackHost,
        onHandleAction = {
            when (it) {
                is Actions.Navigation.UpdatePressed -> onGoToDetail(it.item)
                Actions.Navigation.AddPressed -> onNavigateToAdd()
                is Actions.Interaction -> viewModel.onHandleIntent(it)
                Actions.Navigation.GoToProfile -> onNavigateToProfile()
            }
        },
    )
}