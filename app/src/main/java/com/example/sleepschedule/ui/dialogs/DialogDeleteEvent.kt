package com.example.sleepschedule.ui.dialogs

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sleepschedule.R
import com.example.sleepschedule.common.TimeHelper
import com.example.sleepschedule.common.scheduleEventMockList
import com.example.sleepschedule.ui.components.HeaderDialog
import com.ulises.theme.SleepScheduleTheme
import models.ScheduledEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogDeleteEvent(
    event: ScheduledEvent?,
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                HeaderDialog(onClickIcon = onDismiss)
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_delete),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(id = R.string.dialog_delete_message))
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                val time = TimeHelper.toHumanReadable(event?.date ?: "")
                                append(" ${time.split(',')[1]}")
                            }
                            append(stringResource(id = R.string.dialog_delete_message_end_message))
                        },
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(onClick = { onDelete() }) {
                        Text(text = stringResource(id = R.string.dialog_delete_button))
                    }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Prev_DialogDeleteEvent() {
    SleepScheduleTheme {
        DialogDeleteEvent(
            event = scheduleEventMockList[0],
            onDismiss = {},
            onDelete = {}
        )
    }
}