package com.example.sleepschedule.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sleepschedule.ui.theme.SleepScheduleTheme
import com.example.sleepschedule.ui.viewmodels.ScheduleDateDetailViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sleepschedule.R
import com.example.sleepschedule.common.day
import com.example.sleepschedule.common.month
import com.example.sleepschedule.common.year
import com.example.sleepschedule.ui.components.TopBar
import com.example.sleepschedule.ui.viewmodels.TextFieldType
import java.util.*

@Composable
fun ScheduleDateDetail(
    viewModel: ScheduleDateDetailViewModel = viewModel(),
    onNavigateBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val dialog = createCalendarDialog(LocalContext.current) { year, month, day ->
        viewModel.onDateSelected(year, month, day)
    }

    Scaffold(
        topBar = { TopBar { onNavigateBackClick() } }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .padding(16.dp)

        ) {
            Text(
                text = stringResource(id = R.string.title_add_element),
                fontSize = 24.sp
            )
            TextField(
                value = uiState.createdText,
                onValueChange = { text ->
                    viewModel.onUpdateTextField(
                        type = TextFieldType.CreatedBy,
                        text = text
                    )
                },
                enabled = !uiState.isLoading,
                singleLine = true,
                label = { Text(text = stringResource(id = R.string.add_schedule_person)) },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)
            ) {
                TextField(
                    value = uiState.dateText,
                    onValueChange = { },
                    enabled = false,
                    label = { Text(text = stringResource(id = R.string.add_schedule_date)) },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    enabled = !uiState.isLoading,
                    modifier = Modifier.fillMaxHeight(),
                    onClick = { dialog.show() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = null
                    )
                }
            }
            Button(
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.onAddSchedule() }
            ) {
                Text(text = "Add day")
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
        { _: DatePicker, year: Int, month: Int, day: Int ->
            onDateSelected(year, month, day)
        }, mYear, mMonth, mDay
    )
    return mDatePickerDialog
}

@Preview
@Composable
fun Prev_ScheduleDateDetail() {
    SleepScheduleTheme {
        ScheduleDateDetail {}
    }
}