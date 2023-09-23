package com.example.sleepschedule.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sleepschedule.R
import com.example.sleepschedule.common.scheduleEventMockList
import com.ulises.components.FabButton
import com.ulises.components.indicators.LoadingIndicator
import com.example.sleepschedule.ui.components.ScheduleItem
import com.example.sleepschedule.ui.dialogs.DialogDeleteEvent
import com.example.sleepschedule.ui.dialogs.DialogFeedback
import com.ulises.theme.SleepScheduleTheme
import com.example.sleepschedule.ui.utils.DialogType
import com.example.sleepschedule.ui.viewmodels.MainScheduleViewModel
import models.ScheduledEvent

@Composable
fun ScheduleListRoute(
    viewModel: MainScheduleViewModel = hiltViewModel(),
    onNavigateToAdd: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ScheduleListScreen(
        uiState = uiState,
        onNavigateToAdd = onNavigateToAdd,
        onClickItem = viewModel::onClickItemForRotation,
        onDialogChangeVisibility = viewModel::onDialogCloseVisibilityChange,
        onUpdateRating = viewModel::onUpdateRating,
        onDeleteEvent = viewModel::onDeleteScheduleEvent,
    )
}

@Composable
private fun ScheduleListScreen(
    uiState: MainScheduleViewModel.UiState,
    onNavigateToAdd: () -> Unit,
    onClickItem: (ScheduledEvent) -> Unit,
    onDialogChangeVisibility: (DialogType, Boolean, ScheduledEvent?) -> Unit,
    onUpdateRating: (event: ScheduledEvent?, rating: Int) -> Unit,
    onDeleteEvent: (eventId: String?) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            if (!uiState.isLoading) {
                FabButton(
                    icon = R.drawable.ic_add,
                    onClick = onNavigateToAdd,
                )
            }
        }
    ) { paddingValues ->
        if (uiState.showDialogDelete) {
            DialogDeleteEvent(
                event = uiState.selectedEvent,
                onDismiss = { onDialogChangeVisibility(DialogType.Delete, false, null) },
                onDelete = { onDeleteEvent(uiState.selectedEvent?.id) }
            )
        }
        if (uiState.showDialogRating) {
            DialogFeedback(
                event = uiState.selectedEvent,
                onDismiss = { onDialogChangeVisibility(DialogType.Rating, false, null) },
                onUpdateRating = {
                    onUpdateRating(uiState.selectedEvent, it)
                }
            )
        }
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LoadingIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        } else {
            if (uiState.scheduleEvents.isNullOrEmpty()) {
                EmptyScheduleScreen(Modifier.padding(paddingValues))
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 82.dp
                    ),
                    modifier = Modifier.padding(paddingValues)
                ) {
                    items(
                        items = uiState.scheduleEvents,
                        key = { it.id }
                    ) {
                        ScheduleItem(
                            item = it,
                            onClickUpdateFeedback = {
                                onDialogChangeVisibility(DialogType.Rating, true, it)
                            },
                            onClickDelete = {
                                onDialogChangeVisibility(DialogType.Delete, true, it)
                            },
                            onClickItem = onClickItem
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrevMainScheduleScreen() {
    SleepScheduleTheme {
        ScheduleListScreen(
            uiState = MainScheduleViewModel.UiState(
                scheduleEvents = scheduleEventMockList
            ),
            onNavigateToAdd = {},
            onClickItem = {},
            onDialogChangeVisibility = { _, _, _ -> },
            onUpdateRating = { _, _ -> },
            onDeleteEvent = {},
        )
    }
}