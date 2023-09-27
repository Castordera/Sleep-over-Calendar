package com.ulises.components.pickers

import android.content.res.Configuration
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ulises.theme.SleepScheduleTheme
import androidx.compose.material3.DatePickerDialog as ComposeDatePickerDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    isVisible: Boolean,
    datePickerState: DatePickerState = rememberDatePickerState(),
    onSelectDate: (Long?) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    if (!isVisible) return

    ComposeDatePickerDialog(
        confirmButton = {
            TextButton(onClick = { onSelectDate(datePickerState.selectedDateMillis) }) {
                Text(text = "Save")
            }
        },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevDatePicker() {
    SleepScheduleTheme {
        DatePickerDialog(
            isVisible = true
        )
    }
}