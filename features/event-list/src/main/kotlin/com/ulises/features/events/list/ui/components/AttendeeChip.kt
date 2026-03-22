package com.ulises.features.events.list.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.theme.SleepScheduleTheme
import models.Attendee

@Composable
internal fun AttendeeChip(
    attendee: Attendee,
) {
    //Todo(Change this if necessary)
    val colorDemo = Color.White
    Row(
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(colorDemo.copy(alpha = 0.15f))
                .border(1.5.dp, colorDemo.copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = attendee.name.first().toString(),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = colorDemo,
                    fontSize = 15.sp,
                ),
            )
        }

        Column {
            Text(
                text = attendee.name,
                style = MaterialTheme.typography.titleMedium,
            )
            MoodIndicator(mood = attendee.mood)
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewAttendeeChip() {
    SleepScheduleTheme {
        Surface {
            AttendeeChip(
                attendee = Attendee("Ulises", Attendee.Mood.GOOD)
            )
        }
    }
}
