package com.ulises.features.event.add.contents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.NightlightRound
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.common.time.utils.TimeHelper.toFormat
import com.ulises.common.time.utils.TimeHelper.toMillis
import com.ulises.components.pickers.DatePickerDialog
import com.ulises.components.toolbar.AppTopBar
import com.ulises.features.event.add.components.FormSection
import com.ulises.features.event.add.components.MoodSelector
import com.ulises.features.event.add.components.PersonToggleChip
import com.ulises.features.event.add.models.Actions
import com.ulises.features.event.add.models.UiState
import com.ulises.theme.LavenderGlow
import com.ulises.theme.NightDeep
import com.ulises.theme.NightElevated
import com.ulises.theme.NightRim
import com.ulises.theme.RosePetal
import com.ulises.theme.SleepScheduleTheme
import com.ulises.theme.TextMuted
import com.ulises.theme.TextPrimary

@Composable
fun ScheduleDateContentV2(
    uiState: UiState = UiState(),
    onHandleAction: (Actions) -> Unit = {},
) {
    var comments by remember { mutableStateOf("") }
    val maxChars = 250
    val canSave by remember(uiState.selectedAttendees) { derivedStateOf { uiState.selectedAttendees.isNotEmpty() } }

    //
    if (uiState.addComplete) {
        LaunchedEffect(Unit) {
            onHandleAction(Actions.Navigation.NavigateBack)
        }
    }
    //
    DatePickerDialog(
        isVisible = uiState.isDateDialogVisible,
        datePickerState = rememberDatePickerState(uiState.selectedDate.toMillis()),
        onSelectDate = { millis -> onHandleAction(Actions.Interaction.SelectDate(millis)) },
        onDismiss = { onHandleAction(Actions.Interaction.DismissCalendarDialog) }
    )
    //
    Scaffold(
        containerColor = NightDeep,
        topBar = {
            AppTopBar(
                title = "Nueva pijamada",
                withIcon = false,
                onNavigateBack = { onHandleAction(Actions.Navigation.NavigateBack) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            FormSection(title = "Selecciona la fecha") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = uiState.selectedDate.toFormat(),
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                        modifier = Modifier.weight(1f),
                        color = TextPrimary,
                    )
                    Surface(
                        onClick = { onHandleAction(Actions.Interaction.DisplayCalendarDialog(true)) },
                        shape = RoundedCornerShape(14.dp),
                        color = LavenderGlow.copy(alpha = 0.15f),
                        border = BorderStroke(1.dp, LavenderGlow.copy(alpha = 0.35f)),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                Icons.Rounded.CalendarMonth,
                                contentDescription = "Elegir fecha",
                                tint = LavenderGlow,
                                modifier = Modifier.size(24.dp),
                            )
                        }
                    }
                }
            }
            FormSection(title = "¿Quién se quedó?") {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    uiState.availableAttendees.forEach { attendee ->
                        val isSelected = uiState.selectedAttendees.any { it.name == attendee.name }
                        PersonToggleChip(
                            name = attendee.name,
                            isSelected = isSelected,
                            onClick = {
                                onHandleAction(Actions.Interaction.SelectAttendeeClicked(attendee))
                            },
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = uiState.selectedAttendees.isNotEmpty(),
            ) {
                FormSection(title = "¿Cómo se portaron?") {
                    Column(
                        modifier = Modifier.animateContentSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        uiState.selectedAttendees.forEach { attendee ->
                            MoodSelector(attendee = attendee) {
                                onHandleAction(Actions.Interaction.MoodSelected(attendee, it))
                            }
                        }
                    }
                }
            }

            FormSection(title = "Comentarios") {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = NightElevated,
                    border = BorderStroke(1.dp, NightRim),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column {
                        OutlinedTextField(
                            value = comments,
                            onValueChange = { if (it.length <= maxChars) comments = it },
                            placeholder = {
                                Text(
                                    "Escribe algo sobre esta noche…",
                                    style = MaterialTheme.typography.bodyLarge.copy(color = TextMuted),
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary,
                                cursorColor = LavenderGlow,
                            ),
                            minLines = 4,
                            maxLines = 7,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                            ),
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Text(
                            text = "${comments.length} / $maxChars",
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 14.dp, bottom = 10.dp),
                            color = if (comments.length > maxChars * 0.9) RosePetal else TextMuted,
                        )
                    }
                }
            }
            Spacer(Modifier.height(4.dp))
            Button(
                onClick = {
                    onHandleAction(
                        Actions.Interaction.AddScheduledEventClicked(
                            comments
                        )
                    )
                },
                enabled = canSave,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LavenderGlow,
                    contentColor = Color(0xFF1A0D4A),
                    disabledContainerColor = NightElevated,
                    disabledContentColor = TextMuted,
                ),
            ) {
                Icon(
                    Icons.Rounded.NightlightRound,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Guardar noche",
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 15.sp),
                )
            }
            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

@Preview
@Composable
private fun PrevScheduleDateContentV2() {
    SleepScheduleTheme {
        ScheduleDateContentV2()
    }
}