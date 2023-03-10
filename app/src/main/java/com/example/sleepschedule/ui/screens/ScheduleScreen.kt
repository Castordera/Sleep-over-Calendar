package com.example.sleepschedule.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sleepschedule.ui.components.FAButton
import com.example.sleepschedule.ui.components.LoadingIndicator
import com.example.sleepschedule.ui.components.ScheduleItem
import com.example.sleepschedule.ui.dialogs.DialogDeleteEvent
import com.example.sleepschedule.ui.dialogs.DialogFeedback
import com.example.sleepschedule.ui.theme.SleepScheduleTheme
import com.example.sleepschedule.ui.utils.DialogType
import com.example.sleepschedule.ui.viewmodels.MainScheduleViewModel
import models.ScheduledEvent

@Composable
fun MainScheduleScreen(
    scheduleViewModel: MainScheduleViewModel = viewModel(),
    onNavigateToAdd: () -> Unit
) {
    val uiState by scheduleViewModel.uiState.collectAsState()

    Scaffold (
        floatingActionButton = {
            if (!uiState.isLoading) {
                FAButton { onNavigateToAdd() }
            }
        }
    ) { paddingValues ->
        if (uiState.showDialogDelete) {
            DialogDeleteEvent(
                event = uiState.selectedEvent,
                onDismiss = {
                    scheduleViewModel.onDialogCloseVisibilityChange(
                        dialogType = DialogType.Delete,
                        isVisible = false
                    )
                },
                onDelete = {
                    scheduleViewModel.onDeleteScheduleEvent(uiState.selectedEvent?.id)
                }
            )
        }
        if (uiState.showDialogRating) {
            DialogFeedback(
                event = uiState.selectedEvent,
                onDismiss = {
                    scheduleViewModel.onDialogCloseVisibilityChange(
                        dialogType = DialogType.Rating,
                        isVisible = false
                    )
                },
                onUpdateRating = {
                    scheduleViewModel.onUpdateRating(uiState.selectedEvent, it)
                }
            )
        }
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LoadingIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            if (uiState.scheduleEvents.isNullOrEmpty()) {
                EmptyScheduleScreen(Modifier.padding(paddingValues))
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.padding(paddingValues)
                ) {
                    items(
                        items = uiState.scheduleEvents as List<ScheduledEvent>,
                        key = { it.id }
                    ) {
                        ScheduleItem(
                            item = it,
                            onClickUpdateFeedback = {
                                scheduleViewModel.onDialogCloseVisibilityChange(
                                    dialogType = DialogType.Rating,
                                    isVisible = true,
                                    event = it
                                )
                            },
                            onClickDelete = {
                                scheduleViewModel.onDialogCloseVisibilityChange(
                                    dialogType = DialogType.Delete,
                                    isVisible = true,
                                    event = it
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Prev_MainScheduleScreen() {
    SleepScheduleTheme {
        MainScheduleScreen {}
    }
}