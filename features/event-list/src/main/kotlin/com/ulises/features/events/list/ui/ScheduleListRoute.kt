package com.ulises.features.events.list.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulises.features.events.list.models.Intents
import models.ScheduledEvent

@Composable
fun ScheduleListRoute(
    viewModel: MainScheduleViewModel = hiltViewModel(),
    onNavigateToAdd: () -> Unit,
    onGoToDetail: (ScheduledEvent) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ScheduleListScreen(
        uiState = uiState,
        onHandleIntent = {
            when (it) {
                is Intents.UpdatePressed -> onGoToDetail(it.item)
                Intents.AddPressed -> onNavigateToAdd()
                else -> viewModel.onHandleIntent(it)
            }
        },
    )
}