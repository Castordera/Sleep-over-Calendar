package com.example.sleepschedule.ui.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.sleepschedule.R
import com.example.sleepschedule.ui.theme.SleepScheduleTheme

@Composable
fun TopBar(
    title: String = "",
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(imageResource = R.drawable.ic_arrow_back) {
                onBackClick()
            }
        }
    )
}

@Preview
@Composable
fun Prev_TopBar() {
    SleepScheduleTheme {
        TopBar {}
    }
}