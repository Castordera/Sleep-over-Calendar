package com.ulises.features.events.list.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ulises.theme.LavenderGlow
import com.ulises.theme.NightElevated
import com.ulises.theme.NightRim
import com.ulises.theme.TextSecondary

@Composable
internal fun YearList(
    years: List<String>,
    selectedYear: String,
    onYearSelected: (String) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(years, key = { it }) { year ->
            val selected = year == selectedYear
            Surface(
                onClick = { onYearSelected(year) },
                shape = MaterialTheme.shapes.extraLarge,
                color = if (selected) LavenderGlow else NightElevated,
                border = if (!selected) BorderStroke(1.dp, NightRim) else null,
                modifier = Modifier.height(36.dp),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(horizontal = 20.dp),
                ) {
                    Text(
                        text = year,
                        style = MaterialTheme.typography.labelLarge,
                        color = if (selected) Color(0xFF1A0D4A) else TextSecondary,
                    )
                }
            }
        }
    }
}