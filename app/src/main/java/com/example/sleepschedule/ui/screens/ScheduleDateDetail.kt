package com.example.sleepschedule.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sleepschedule.R
import com.example.sleepschedule.common.day
import com.example.sleepschedule.common.month
import com.example.sleepschedule.common.year
import com.example.sleepschedule.ui.components.TopBar
import com.example.sleepschedule.ui.theme.SleepScheduleTheme
import com.example.sleepschedule.ui.viewmodels.ScheduleDateDetailViewModel
import com.example.sleepschedule.ui.viewmodels.TextFieldType
import java.util.Calendar

@Composable
fun ScheduleDetailRoute(
    viewModel: ScheduleDateDetailViewModel = hiltViewModel(),
    onNavigateBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ScheduleDateDetail(
        uiState = uiState,
        onDateSelected = viewModel::onDateSelected,
        onUpdateTextField = viewModel::onUpdateTextField,
        onUpdateCommentField = viewModel::onUpdateTextField,
        onAddEvent = viewModel::onAddSchedule,
        onNavigateBackClick = onNavigateBackClick
    )
}

@Composable
fun ScheduleDateDetail(
    uiState: ScheduleDateDetailViewModel.UiState,
    onDateSelected: (year: Int, month: Int, day: Int) -> Unit,
    onUpdateTextField: (type: TextFieldType, text: String) -> Unit,
    onUpdateCommentField: (type: TextFieldType, text: String) -> Unit,
    onAddEvent: () -> Unit,
    onNavigateBackClick: () -> Unit
) {
    //Todo(Change it to Compose Date Picker)
    val dialog = createCalendarDialog(LocalContext.current, onDateSelected)

    if (uiState.addComplete) {
        LaunchedEffect(Unit) {
            onNavigateBackClick()
        }
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
                    onUpdateTextField(TextFieldType.CreatedBy, text)
                },
                enabled = !uiState.isLoading,
                singleLine = true,
                label = { Text(text = stringResource(id = R.string.add_schedule_person)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )
            Column {
                Text(
                    text = "Selecciona la fecha:",
                    fontSize = 12.sp
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            val date = uiState.dateText.split(",")
                            append(date[1])
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
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
            }
            TextField(
                value = "Renata",
                onValueChange = {},
                label = { Text(text = stringResource(id = R.string.add_schedule_kid_name)) },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                label = { Text(text = "Comentarios") },
                value = uiState.comments,
                onValueChange = { value ->
                    onUpdateCommentField(TextFieldType.Comment, value)
                },
                maxLines = 3,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                enabled = !uiState.isLoading && uiState.isReadyToSend,
                modifier = Modifier.fillMaxWidth(),
                onClick = onAddEvent
            ) {
                Text(text = stringResource(id = R.string.add_schedule_button_add))
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
        ScheduleDateDetail(
            uiState = ScheduleDateDetailViewModel.UiState(
                dateText = "Monday,12 de March del 2023"
            ),
            onNavigateBackClick = {},
            onDateSelected = { _, _, _ -> },
            onUpdateTextField = { _, _ -> },
            onUpdateCommentField = { _, _ -> },
            onAddEvent = {}
        )
    }
}