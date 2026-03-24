package com.ulises.features.event.add.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SentimentNeutral
import androidx.compose.material.icons.rounded.SentimentVeryDissatisfied
import androidx.compose.material.icons.rounded.SentimentVerySatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ulises.theme.DangerRed
import com.ulises.theme.MoodNeutral
import com.ulises.theme.NightElevated
import com.ulises.theme.NightRim
import com.ulises.theme.SuccessGreen
import com.ulises.theme.TextMuted
import models.Attendee

@Composable
internal fun MoodSelector(
    attendee: Attendee,
    onClick: (Attendee.Mood) -> Unit,
) {
    val moods = listOf(
        Triple(Attendee.Mood.GOOD, Icons.Rounded.SentimentVerySatisfied, "Feliz"),
        Triple(Attendee.Mood.NEUTRAL, Icons.Rounded.SentimentNeutral, "Normal"),
        Triple(Attendee.Mood.BAD, Icons.Rounded.SentimentVeryDissatisfied, "Triste"),
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = attendee.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f),
        )
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            moods.forEach { (mood, icon, label) ->
                val isSelected = attendee.mood == mood
                val tint = when (mood) {
                    Attendee.Mood.GOOD -> SuccessGreen
                    Attendee.Mood.NEUTRAL -> MoodNeutral
                    Attendee.Mood.BAD -> DangerRed
                }
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) tint.copy(alpha = 0.18f) else NightElevated
                        )
                        .border(
                            1.dp,
                            if (isSelected) tint.copy(alpha = 0.55f) else NightRim,
                            CircleShape,
                        )
                        .clickable { onClick(mood) },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        icon,
                        contentDescription = label,
                        tint = if (isSelected) tint else TextMuted,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        }
    }
}