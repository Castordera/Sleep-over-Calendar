package com.ulises.features.event.add.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ulises.common.time.utils.TimeHelper.toHumanReadable
import com.ulises.common.time.utils.TimeHelper.toMillis
import com.ulises.components.pickers.DatePickerDialog
import com.ulises.components.toolbar.TopBar
import com.ulises.features.event.add.R
import com.ulises.features.event.add.models.Intents
import com.ulises.features.event.add.models.UiState
import com.ulises.features.event.add.ui.ScheduleDateDetailViewModel.Companion.MAX_COMMENT_LENGTH
import com.ulises.theme.SleepScheduleTheme
import models.Kid
import java.time.LocalDate

@Composable
fun ScheduleDetailRoute(
    viewModel: ScheduleDateDetailViewModel = hiltViewModel(),
    onNavigateBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ScheduleDateDetailScreen(
        uiState = uiState,
        onHandleIntent = {
            when (it) {
                Intents.NavigateBack -> onNavigateBackClick()
                else -> viewModel.onHandleIntent(it)
            }
        },
        getTextField = viewModel::getTextField,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScheduleDateDetailScreen(
    uiState: UiState,
    onHandleIntent: (Intents) -> Unit = {},
    getTextField: (TextFieldType) -> String = { "" },
) {
    val isReadyToSend by remember(uiState) {
        derivedStateOf {
            !uiState.isLoading && uiState.allTextFieldsFilled && uiState.selectedKids.isNotEmpty()
        }
    }
    if (uiState.addComplete) {
        LaunchedEffect(Unit) {
            onHandleIntent(Intents.NavigateBack)
        }
    }

    Scaffold(
        topBar = {
            TopBar(title = "Nueva pijamada") {
                onHandleIntent(Intents.NavigateBack)
            }
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .padding(16.dp),

            ) {
            DatePickerDialog(
                isVisible = uiState.isDateDialogVisible,
                datePickerState = rememberDatePickerState(uiState.selectedDate.toMillis()),
                onSelectDate = { millis -> onHandleIntent(Intents.SelectDate(millis)) },
                onDismiss = { onHandleIntent(Intents.DisplayCalendarDialog(false)) }
            )
            Column {
                Text(
                    text = "Selecciona la fecha:",
                    fontSize = 12.sp
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            val date = uiState.selectedDate.toHumanReadable().split(",")
                            append(date[1])
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        enabled = !uiState.isLoading,
                        modifier = Modifier.fillMaxHeight(),
                        onClick = { onHandleIntent(Intents.DisplayCalendarDialog(true)) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = null
                        )
                    }
                }
            }
            Text("¿Quién hizo la pijamada?", fontSize = 12.sp)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.kids, key = { it.name }) {
                    FilterChip(
                        selected = uiState.selectedKids.contains(it),
                        onClick = { onHandleIntent(Intents.SelectKid(it)) },
                        label = { Text(it.name) },
                    )
                }
            }
            OutlinedTextField(
                label = { Text(text = "Comentarios") },
                value = getTextField(TextFieldType.Comment),
                onValueChange = { text ->
                    onHandleIntent(Intents.UpdateTextField(TextFieldType.Comment, text))
                },
                maxLines = 3,
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    Text(
                        text = "Total de letras ${getTextField(TextFieldType.Comment).length}/$MAX_COMMENT_LENGTH",
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
            Button(
                enabled = isReadyToSend,
                modifier = Modifier.fillMaxWidth(),
                onClick = { onHandleIntent(Intents.AddItem) }
            ) {
                Text(text = stringResource(id = R.string.add_schedule_button_add))
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, fontScale = 2f)
@Composable
fun PrevScheduleDateDetail() {
    SleepScheduleTheme {
        ScheduleDateDetailScreen(
            uiState = UiState(
                selectedDate = LocalDate.now(),
                kids = listOf(
                    Kid("Renata", 0),
                    Kid("Lando", 0),
                )
            ),
        )
    }
}