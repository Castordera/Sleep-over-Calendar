package com.ulises.features.event.add.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ulises.theme.LavenderGlow
import com.ulises.theme.LavenderLight
import com.ulises.theme.NightElevated
import com.ulises.theme.NightRim
import com.ulises.theme.TextSecondary

@Composable
internal fun PersonToggleChip(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val bgColor = if (isSelected) LavenderGlow.copy(alpha = 0.18f) else NightElevated
    val borderColor = if (isSelected) LavenderGlow.copy(alpha = 0.6f) else NightRim
    val textColor = if (isSelected) LavenderLight else TextSecondary

    Surface(
        onClick = onClick,
        shape = MaterialTheme.shapes.extraLarge,
        color = bgColor,
        border = BorderStroke(1.dp, borderColor),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            if (isSelected) {
                Icon(
                    Icons.Rounded.Check,
                    contentDescription = null,
                    tint = LavenderGlow,
                    modifier = Modifier.size(14.dp),
                )
            }
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium.copy(color = textColor),
            )
        }
    }
}