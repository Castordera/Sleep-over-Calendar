package com.ulises.features.events.list.ui.contents

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ulises.components.toolbar.AppTopBar
import com.ulises.components.toolbar.BackgroundStar
import com.ulises.components.toolbar.TopActionButton
import com.ulises.features.events.list.models.Actions
import com.ulises.features.events.list.models.UiState
import com.ulises.features.events.list.ui.components.SleepEventItem
import com.ulises.features.events.list.ui.components.YearList
import com.ulises.features.events.list.ui.dialogs.DeleteConfirmDialog
import com.ulises.features.events.list.utils.scheduleEventMockList
import com.ulises.theme.LavenderGlow
import com.ulises.theme.SleepScheduleTheme

@Composable
internal fun ScheduleListV2Content(
    uiState: UiState,
    snackHost: SnackbarHostState,
    onHandleAction: (Actions) -> Unit = {},
) {
    //  Dialogs
    if (uiState.deleteDialogState.isVisible && uiState.deleteDialogState.event != null) {
        DeleteConfirmDialog(
            event = uiState.deleteDialogState.event,
            onConfirm = { onHandleAction(Actions.Interaction.DeleteItem) },
            onDismiss = { onHandleAction(Actions.Interaction.DismissDeleteDialog) },
        )
    }
    //
    if (uiState.message.isNotEmpty()) {
        LaunchedEffect(uiState.message) {
            snackHost.showSnackbar(uiState.message)
            onHandleAction(Actions.Interaction.DismissMessage)
        }
    }
    //
    Scaffold(
        snackbarHost = { SnackbarHost(snackHost) },
        topBar = {
            AppTopBar(
                title = "Pijamadas",
                actions = {
                    TopActionButton(
                        icon = Icons.Rounded.Person,
                        description = "Perfil",
                    ) {
                        onHandleAction(Actions.Navigation.GoToProfile)
                    }
                }
            )
        },
        floatingActionButton = {
            if (uiState.scheduleEvents.isNullOrEmpty()) return@Scaffold
            //
            ExtendedFloatingActionButton(
                onClick = { onHandleAction(Actions.Navigation.AddPressed) },
                containerColor = LavenderGlow,
                contentColor = Color(0xFF1A0D4A),
                shape = MaterialTheme.shapes.extraLarge,
                icon = {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                },
                text = {
                    Text(
                        "Agregar",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFF1A0D4A)
                    )
                },
            )
        }
    ) { innerPadding ->
        BackgroundStar {
            //  Data
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                YearList(uiState.years, uiState.selectedYear) {
                    onHandleAction(Actions.Interaction.ChangeSelectedYear(it))
                }
                //  Empty State
                if (uiState.scheduleEvents.isNullOrEmpty()) {
                    EmptyScheduleContent(
                        modifier = Modifier.padding(innerPadding),
                        selectedYear = uiState.selectedYear,
                        onAddClick = { onHandleAction(Actions.Navigation.AddPressed) }
                    )
                    return@BackgroundStar
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(uiState.scheduleEvents, key = { it.id }) { event ->
                        SleepEventItem(
                            entry = event,
                            isExpanded = event.isExpanded,
                            onToggle = { onHandleAction(Actions.Interaction.ClickItem(event)) },
                            onDeleteClick = {
                                onHandleAction(Actions.Interaction.ShowDeleteDialog(event))
                            },
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
private fun PrevMainScheduleContent() {
    SleepScheduleTheme {
        ScheduleListV2Content(
            snackHost = SnackbarHostState(),
            uiState = UiState(
                years = listOf("2015", "2020"),
                selectedYear = "2015",
                scheduleEvents = scheduleEventMockList
            ),
        )
    }
}