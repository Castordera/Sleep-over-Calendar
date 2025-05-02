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
import androidx.compose.runtime.remember
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
import com.ulises.features.events.list.models.Intents
import com.ulises.features.events.list.models.UiState
import com.ulises.features.events.list.utils.scheduleEventMockList
import com.ulises.theme.SleepScheduleTheme

@Composable
internal fun ScheduleListScreen(
    uiState: UiState,
    onHandleIntent: (Intents) -> Unit = {},
) {
    val snackBarHostState = remember { SnackbarHostState() }

    if (uiState.error != null) {
        LaunchedEffect(uiState.error) {
            snackBarHostState.showSnackbar(uiState.error)
            onHandleIntent(Intents.ClearError)
        }
    }

    Scaffold(
        floatingActionButton = {
            if (!uiState.isLoading) {
                FabButton(
                    icon = R.drawable.ic_add,
                    onClick = { onHandleIntent(Intents.AddPressed) },
                )
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Top)
    ) { paddingValues ->
        if (uiState.showDialogDelete) {
            DialogDeleteEvent(
                event = uiState.selectedEvent,
                onDismiss = {
                    onHandleIntent(Intents.ChangeDeleteDialogState(false, null))
                },
                onDelete = { onHandleIntent(Intents.DeleteItem) },
            )
        }
        if (uiState.showDialogRating) {
            DialogFeedback(
                event = uiState.selectedEvent,
                kid = uiState.selectedKid,
                onDismiss = {
                    onHandleIntent(Intents.ChangeRateDialogState(false, null, null))
                },
                onUpdateRating = { onHandleIntent(Intents.UpdateRating(it)) }
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
                        key = { it.id },
                    ) { event ->
                        ScheduleItem(
                            item = event,
                            onClickUpdateFeedback = { kid ->
                                onHandleIntent(Intents.ChangeRateDialogState(true, event, kid))
                            },
                            onClickDelete = {
                                onHandleIntent(Intents.ChangeDeleteDialogState(true, event))
                            },
                            onClickItem = { onHandleIntent(Intents.ClickItem(event)) },
                            onClickEdit = { onHandleIntent(Intents.UpdatePressed(event)) },
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
            uiState = UiState(scheduleEvents = scheduleEventMockList),
        )
    }
}