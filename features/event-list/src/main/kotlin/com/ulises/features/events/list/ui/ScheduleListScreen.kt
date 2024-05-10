package com.ulises.features.events.list.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ulises.components.FabButton
import com.ulises.components.indicators.LoadingIndicator
import com.ulises.features.events.list.R
import com.ulises.features.events.list.components.ScheduleItem
import com.ulises.features.events.list.dialogs.DialogDeleteEvent
import com.ulises.features.events.list.dialogs.DialogFeedback
import com.ulises.features.events.list.models.DialogType
import com.ulises.features.events.list.models.UiState
import com.ulises.features.events.list.utils.scheduleEventMockList
import com.ulises.theme.SleepScheduleTheme
import models.Kid
import models.ScheduledEvent

@Composable
internal fun ScheduleListScreen(
    uiState: UiState,
    snackBarHostState: SnackbarHostState,
    onNavigateToAdd: () -> Unit,
    onClickItem: (ScheduledEvent) -> Unit,
    onDialogChangeVisibility: (DialogType, Boolean, ScheduledEvent?, Kid?) -> Unit,
    onUpdateRating: (event: ScheduledEvent?, rating: Int) -> Unit,
    onDeleteEvent: (eventId: String?) -> Unit,
    onErrorDisplayed: () -> Unit,
) {
    if (uiState.error != null) {
        LaunchedEffect(uiState.error) {
            snackBarHostState.showSnackbar(uiState.error)
            onErrorDisplayed()
        }
    }

    Scaffold(
        floatingActionButton = {
            if (!uiState.isLoading) {
                FabButton(
                    icon = R.drawable.ic_add,
                    onClick = onNavigateToAdd,
                )
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Top)
    ) { paddingValues ->
        if (uiState.showDialogDelete) {
            DialogDeleteEvent(
                event = uiState.selectedEvent,
                onDismiss = { onDialogChangeVisibility(DialogType.Delete, false, null, null) },
                onDelete = { onDeleteEvent(uiState.selectedEvent?.id) }
            )
        }
        if (uiState.showDialogRating) {
            DialogFeedback(
                event = uiState.selectedEvent,
                kid = uiState.selectedKid,
                onDismiss = { onDialogChangeVisibility(DialogType.Rating, false, null, null) },
                onUpdateRating = { onUpdateRating(uiState.selectedEvent, it) }
            )
        }
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LoadingIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    isVisible = true,
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
                    ) { event ->
                        ScheduleItem(
                            item = event,
                            onClickUpdateFeedback = { kid ->
                                onDialogChangeVisibility(DialogType.Rating, true, event, kid)
                            },
                            onClickDelete = {
                                onDialogChangeVisibility(DialogType.Delete, true, event, null)
                            },
                            onClickItem = onClickItem,
                            onClickEdit = {},
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, fontScale = 1.5f)
@Composable
private fun PrevMainScheduleScreen() {
    SleepScheduleTheme {
        ScheduleListScreen(
            uiState = UiState(
                scheduleEvents = scheduleEventMockList
            ),
            snackBarHostState = SnackbarHostState(),
            onNavigateToAdd = {},
            onClickItem = {},
            onDialogChangeVisibility = { _, _, _, _ -> },
            onUpdateRating = { _, _ -> },
            onDeleteEvent = {},
            onErrorDisplayed = {},
        )
    }
}