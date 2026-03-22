package com.ulises.features.events.list.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SentimentNeutral
import androidx.compose.material.icons.rounded.SentimentVeryDissatisfied
import androidx.compose.material.icons.rounded.SentimentVerySatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ulises.theme.DangerRed
import com.ulises.theme.MoodNeutral
import com.ulises.theme.SleepScheduleTheme
import com.ulises.theme.SuccessGreen
import models.Attendee

@Composable
internal fun MoodIndicator(mood: Attendee.Mood) {
    val (icon, tint, label) = when (mood) {
        Attendee.Mood.GOOD -> Triple(Icons.Rounded.SentimentVerySatisfied, SuccessGreen, "Bien")
        Attendee.Mood.NEUTRAL -> Triple(Icons.Rounded.SentimentNeutral, MoodNeutral, "Regular")
        Attendee.Mood.BAD -> Triple(Icons.Rounded.SentimentVeryDissatisfied, DangerRed, "Mal")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        Icon(icon, contentDescription = label, tint = tint, modifier = Modifier.size(16.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(color = tint),
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewMoodIndicator() {
    SleepScheduleTheme {
        Surface {
            Column {
                Attendee.Mood.entries.forEach {
                    MoodIndicator(it)
                }
            }
        }
    }
}