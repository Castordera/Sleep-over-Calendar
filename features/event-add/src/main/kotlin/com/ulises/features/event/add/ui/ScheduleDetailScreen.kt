package com.ulises.features.event.add.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulises.features.event.add.contents.ScheduleDateContentV2
import com.ulises.features.event.add.models.Actions

@Composable
fun ScheduleDetailScreen(
    viewModel: ScheduleDateDetailViewModel = hiltViewModel(),
    onNavigateBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ScheduleDateContentV2(
        uiState = uiState,
        onHandleAction = {
            when (it) {
                Actions.Navigation.NavigateBack -> onNavigateBackClick()
                is Actions.Interaction -> viewModel.onHandleIntent(it)
            }
        },
    )
}