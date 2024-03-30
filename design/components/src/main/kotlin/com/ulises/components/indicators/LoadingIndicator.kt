package com.ulises.components.indicators

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ulises.theme.SleepScheduleTheme

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
) {
    if (isVisible) {
        CircularProgressIndicator(modifier = modifier)
    }
}

@Preview
@Composable
fun Prev_LoadingIndicator() {
    SleepScheduleTheme {
        LoadingIndicator(
            isVisible = true
        )
    }
}