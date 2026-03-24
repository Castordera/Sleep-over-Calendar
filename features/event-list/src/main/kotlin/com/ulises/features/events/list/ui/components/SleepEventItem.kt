package com.ulises.features.events.list.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.NightlightRound
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.common.time.utils.TimeHelper.toFormat
import com.ulises.common.time.utils.TimeHelper.toWeekDay
import com.ulises.features.events.list.utils.attendeesMockList
import com.ulises.features.events.list.utils.scheduleEventMockList
import com.ulises.theme.DangerRed
import com.ulises.theme.LavenderGlow
import com.ulises.theme.NightElevated
import com.ulises.theme.NightRim
import com.ulises.theme.NightSurface
import com.ulises.theme.RosePetal
import com.ulises.theme.SleepScheduleTheme
import com.ulises.theme.TextMuted
import com.ulises.theme.TextPrimary
import models.ScheduledEvent

@Composable
internal fun SleepEventItem(
    entry: ScheduledEvent,
    isExpanded: Boolean = false,
    onToggle: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    val borderColor by animateColorAsState(
        targetValue = if (isExpanded) LavenderGlow.copy(alpha = 0.45f) else NightRim,
        animationSpec = tween(300),
        label = "border",
    )
    Surface(
        onClick = onToggle,
        shape = MaterialTheme.shapes.medium,
        color = NightSurface,
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            // Línea de acento superior cuando está expandida
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(tween(250)),
                exit = shrinkVertically(tween(200)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(LavenderGlow, RosePetal)
                            )
                        )
                )
            }

            Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp)) {

                // Encabezado: fecha + botón borrar
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        // Día de la semana con ícono luna
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            if (entry.isLegacy) {
                                Text("L")
                            }
                            Icon(
                                Icons.Rounded.NightlightRound,
                                contentDescription = null,
                                tint = LavenderGlow.copy(alpha = 0.7f),
                                modifier = Modifier.size(14.dp),
                            )
                            Text(
                                text = entry.dateScheduled.toWeekDay().uppercase(),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    letterSpacing = 1.2.sp,
                                    color = LavenderGlow.copy(alpha = 0.8f),
                                ),
                            )
                        }
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = entry.dateScheduled.toFormat(),
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = "Agregado por ${entry.createdBy}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    // Botón borrar
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(DangerRed.copy(alpha = 0.08f))
                            .border(1.dp, DangerRed.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
                            .clickable { onDeleteClick() },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            Icons.Rounded.DeleteOutline,
                            contentDescription = "Eliminar",
                            tint = DangerRed,
                            modifier = Modifier.size(17.dp),
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Asistentes
                Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    entry.attendees.forEach { attendee ->
                        AttendeeChip(attendee)
                    }
                }

                // Sección expandida: comentarios
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = fadeIn(tween(300)) + expandVertically(tween(300)),
                    exit = fadeOut(tween(200)) + shrinkVertically(tween(200)),
                ) {
                    Column {
                        Spacer(Modifier.height(16.dp))
                        HorizontalDivider(
                            color = NightRim,
                            thickness = 1.dp,
                        )
                        Spacer(Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                Icons.Rounded.ChatBubbleOutline,
                                contentDescription = null,
                                tint = LavenderGlow,
                                modifier = Modifier.size(16.dp),
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "Comentarios",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = TextPrimary
                                ),
                                modifier = Modifier.weight(1f),
                            )
                            Icon(
                                Icons.Rounded.Edit,
                                contentDescription = "Editar",
                                tint = TextMuted,
                                modifier = Modifier.size(16.dp),
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        if (entry.comments.isNotBlank()) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = NightElevated,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = entry.comments,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(12.dp),
                                )
                            }
                        } else {
                            Text(
                                text = "Sin comentarios aún…",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = TextMuted
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewSleepEventItem() {
    SleepScheduleTheme {
        Surface {
            SleepEventItem(
                scheduleEventMockList[0].copy(
                    isExpanded = true,
                    isLegacy = true,
                    attendees = attendeesMockList,
                ),
            )
        }
    }
}