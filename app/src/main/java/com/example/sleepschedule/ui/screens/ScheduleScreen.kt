package com.example.sleepschedule.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sleepschedule.common.day
import com.example.sleepschedule.common.month
import com.example.sleepschedule.common.year
import com.example.sleepschedule.ui.components.FAButton
import com.example.sleepschedule.ui.components.ScheduleItem
import com.example.sleepschedule.ui.theme.SleepScheduleTheme
import com.example.sleepschedule.ui.viewmodels.MainScheduleViewModel
import java.util.*

@Composable
fun MainScheduleScreen(
    scheduleViewModel: MainScheduleViewModel = viewModel(),
    onNavigateToAdd: () -> Unit
) {
    val uiState by scheduleViewModel.uiState.collectAsState()
    val dialog = createCalendarDialog(LocalContext.current) { year, month, day ->
        println("Date selected: $year - $month - $day")
    }

    Scaffold (
        floatingActionButton = { FAButton { dialog.show() } }
    ) { paddingValues ->
        if (uiState.scheduleEvents.isNullOrEmpty()) {
            EmptyScheduleScreen(Modifier.padding(paddingValues))
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.padding(paddingValues)
            ) {
                items(uiState.scheduleEvents as List<String>) {
                    ScheduleItem(
                        onClickUpdate = {},
                        onClickDelete = {}
                    )
                }
            }
        }
    }
}

private fun createCalendarDialog(
    context: Context,
    onDateSelected: (year: Int, month: Int, day: Int) -> Unit
): DatePickerDialog {
    val calendar = Calendar.getInstance()
    //  Get current day
    val mYear = calendar.year
    val mMonth = calendar.month
    val mDay = calendar.day

    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, mDayOfMonth: Int ->
            onDateSelected(year, month, mDayOfMonth)
        }, mYear, mMonth, mDay
    )
    return mDatePickerDialog
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Prev_MainScheduleScreen() {
    SleepScheduleTheme {
        MainScheduleScreen {}
    }
}