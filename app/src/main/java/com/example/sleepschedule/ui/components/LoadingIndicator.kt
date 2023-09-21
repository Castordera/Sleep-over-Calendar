package com.example.sleepschedule.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ulises.theme.SleepScheduleTheme

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(modifier = modifier)
}

@Preview
@Composable
fun Prev_LoadingIndicator() {
    SleepScheduleTheme {
        LoadingIndicator()
    }
}