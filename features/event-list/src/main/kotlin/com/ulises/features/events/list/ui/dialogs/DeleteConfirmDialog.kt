package com.ulises.features.events.list.ui.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ulises.components.dialogs.BaseDialog
import com.ulises.features.events.list.utils.scheduleEventMockList
import com.ulises.theme.DangerRed
import com.ulises.theme.NightRim
import com.ulises.theme.SleepScheduleTheme
import com.ulises.theme.TextSecondary
import models.ScheduledEvent

@Composable
fun DeleteConfirmDialog(
    event: ScheduledEvent,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    BaseDialog(onDismiss = onDismiss) {
        // Ícono de bote de basura con fondo rojo
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(DangerRed.copy(alpha = 0.12f))
                .border(1.dp, DangerRed.copy(alpha = 0.25f), CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Rounded.DeleteForever,
                contentDescription = null,
                tint = DangerRed,
                modifier = Modifier.size(30.dp),
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "¿Eliminar noche?",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Vas a eliminar la pijamada del\n${event.date}. Esta acción no se puede deshacer.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = TextSecondary,
        )

        Spacer(Modifier.height(28.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // Cancelar
            OutlinedButton(
                onClick = onDismiss,
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(1.dp, NightRim),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = TextSecondary,
                ),
            ) {
                Text("Cancelar", style = MaterialTheme.typography.labelLarge)
            }

            // Eliminar
            Button(
                onClick = onConfirm,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DangerRed,
                    contentColor = Color.White,
                ),
            ) {
                Icon(
                    Icons.Rounded.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(Modifier.width(6.dp))
                Text("Eliminar", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDeleteConfirmDialog() {
    SleepScheduleTheme {
        DeleteConfirmDialog(
            event = scheduleEventMockList[0],
            onConfirm = {},
            onDismiss = {},
        )
    }
}