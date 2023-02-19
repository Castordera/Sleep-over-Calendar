package com.example.sleepschedule.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sleepschedule.ui.components.ScheduleItem
import com.example.sleepschedule.ui.theme.SleepScheduleTheme
import com.example.sleepschedule.ui.viewmodels.MainScheduleViewModel

@Composable
fun MainScheduleScreen(
    modifier: Modifier = Modifier,
    scheduleViewModel: MainScheduleViewModel = viewModel()
) {
    val scheduleItems = listOf("Ulises", "Ricardo", "Castorena", "Caldera")
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier
    ) {
        items(scheduleItems) {
            ScheduleItem(
                onClickUpdate = {},
                onClickDelete = {}
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Prev_MainScheduleScreen() {
    SleepScheduleTheme {
        MainScheduleScreen()
    }
}