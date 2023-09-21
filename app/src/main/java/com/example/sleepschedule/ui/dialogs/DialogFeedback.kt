package com.example.sleepschedule.ui.dialogs

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sleepschedule.R
import com.example.sleepschedule.ui.components.HeaderDialog
import com.example.sleepschedule.ui.components.RatingButton
import com.ulises.theme.SleepScheduleTheme
import com.example.sleepschedule.ui.utils.RatingType
import models.ScheduledEvent

private val ratingValues = arrayOf(RatingType.Bad, RatingType.Neutral, RatingType.Good)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogFeedback(
    event: ScheduledEvent?,
    onDismiss: () -> Unit,
    onUpdateRating: (Int) -> Unit
) {
    var rateSelected by remember { mutableIntStateOf(event?.rating ?: 0) }

    AlertDialog(
        onDismissRequest = onDismiss
    ) {
        Surface {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                HeaderDialog(onClickIcon = onDismiss)
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.dialog_rating_message,
                            event?.kidName.orEmpty()
                        ),
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
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevDialogFeedback() {
    SleepScheduleTheme {
        DialogFeedback(event = null, onDismiss = {}, onUpdateRating = {})
    }
}