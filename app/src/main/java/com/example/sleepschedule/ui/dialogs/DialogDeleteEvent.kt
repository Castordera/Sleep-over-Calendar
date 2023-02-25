package com.example.sleepschedule.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import androidx.compose.ui.window.Dialog
import com.example.sleepschedule.R
import com.example.sleepschedule.ui.components.HeaderDialog
import com.example.sleepschedule.ui.theme.SleepScheduleTheme
import models.ScheduledEvent

@Composable
fun DialogDeleteEvent(
    event: ScheduledEvent?,
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                HeaderDialog { onDismiss() }
                Image(
                    painter = painterResource(id = R.drawable.img_delete),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.dialog_delete_message))
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(" ${event?.date.orEmpty()}")
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

@Preview
@Composable
fun Prev_DialogDeleteEvent() {
    SleepScheduleTheme {
        DialogDeleteEvent(event = null, onDismiss = {}, onDelete = {})
    }
}