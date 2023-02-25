package com.example.sleepschedule.ui.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.sleepschedule.R
import com.example.sleepschedule.ui.components.HeaderDialog
import com.example.sleepschedule.ui.components.RatingButton
import com.example.sleepschedule.ui.theme.SleepScheduleTheme
import com.example.sleepschedule.ui.utils.RatingType
import models.ScheduledEvent

private val ratingValues = arrayOf(RatingType.Bad, RatingType.Neutral, RatingType.Good)

@Composable
fun DialogFeedback(
    event: ScheduledEvent?,
    onDismiss: () -> Unit,
    onUpdateRating: (Int) -> Unit
) {
    var rateSelected by remember { mutableStateOf(event?.rating ?: 0) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                HeaderDialog { onDismiss() }
                Text(
                    text = stringResource(id = R.string.dialog_rating_message, event?.kidName.orEmpty()),
                    fontSize = 18.sp
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(60.dp)
                ) {
                    (ratingValues).forEach { ratingType ->
                        RatingButton(
                            image = ratingType.icon,
                            isSelected = rateSelected == ratingType.value
                        ) {
                            rateSelected = ratingType.value
                        }
                    }
                }
                Button(onClick = { onUpdateRating(rateSelected) }) {
                    Text(text = stringResource(id = R.string.dialog_rating_save))
                }
            }
        }
    }
}

@Preview
@Composable
fun Prev_DialogFeedback() {
    SleepScheduleTheme {
        DialogFeedback(event = null, onDismiss = {}, onUpdateRating = {})
    }
}