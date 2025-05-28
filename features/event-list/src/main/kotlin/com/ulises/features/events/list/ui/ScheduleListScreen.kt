package com.ulises.features.events.list.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import com.ulises.features.events.list.models.Actions
import com.ulises.features.events.list.models.UiState
import com.ulises.features.events.list.utils.scheduleEventMockList
import com.ulises.theme.SleepScheduleTheme

@Composable
internal fun ScheduleListScreen(
    uiState: UiState,
    onHandleIntent: (Actions) -> Unit = {},
) {
    val snackBarHostState = remember { SnackbarHostState() }

    if (uiState.error != null) {
        LaunchedEffect(uiState.error) {
            snackBarHostState.showSnackbar(uiState.error)
            onHandleIntent(Actions.Interaction.ClearError)
        }
    }

    Scaffold(
        floatingActionButton = {
            if (!uiState.isLoading) {
                FabButton(
                    icon = R.drawable.ic_add,
                    onClick = { onHandleIntent(Actions.Navigation.AddPressed) },
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
                    onHandleIntent(Actions.Interaction.ChangeDeleteDialogState(false, null))
                },
                onDelete = { onHandleIntent(Actions.Interaction.DeleteItem) },
            )
        }
        if (uiState.showDialogRating) {
            DialogFeedback(
                event = uiState.selectedEvent,
                kid = uiState.selectedKid,
                onDismiss = {
                    onHandleIntent(Actions.Interaction.ChangeRateDialogState(false, null, null))
                },
                onUpdateRating = { onHandleIntent(Actions.Interaction.UpdateRating(it)) }
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
                Column(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.years) {
                            FilterChip(
                                selected = it == uiState.selectedYear,
                                onClick = { onHandleIntent(Actions.Interaction.ChangeSelectedYear(it)) },
                                label = { Text(it) }
                            )
                        }
                    }
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 82.dp
                        ),
                    ) {
                        items(
                            items = uiState.scheduleEvents,
                            key = { it.id },
                        ) { event ->
                            ScheduleItem(
                                item = event,
                                onClickUpdateFeedback = { kid ->
                                    onHandleIntent(Actions.Interaction.ChangeRateDialogState(true, event, kid))
                                },
                                onClickDelete = {
                                    onHandleIntent(Actions.Interaction.ChangeDeleteDialogState(true, event))
                                },
                                onClickItem = { onHandleIntent(Actions.Interaction.ClickItem(event)) },
                                onClickEdit = { onHandleIntent(Actions.Navigation.UpdatePressed(event)) },
                            )
                        }
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
                years = listOf("2015", "2020"),
                selectedYear = "2015",
                scheduleEvents = scheduleEventMockList
            ),
        )
    }
}