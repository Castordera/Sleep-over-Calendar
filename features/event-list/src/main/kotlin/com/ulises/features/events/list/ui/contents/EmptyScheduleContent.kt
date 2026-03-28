package com.ulises.features.events.list.ui.contents

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ulises.components.screens.AppEmptyContent
import com.ulises.theme.SleepScheduleTheme

@Composable
internal fun EmptyScheduleContent(
    modifier: Modifier = Modifier,
    selectedYear: String,
    onAddClick: () -> Unit = {},
) {
    AppEmptyContent(
        selectedYear = selectedYear,
        onAddClick = onAddClick,
        modifier = modifier.fillMaxSize(),
    )
}

@Preview
@Composable
private fun Prev_EmptyScheduleContent() {
    SleepScheduleTheme {
        Surface {
            EmptyScheduleContent(
                selectedYear = "2020"
            )
        }
    }
}