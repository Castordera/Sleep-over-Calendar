package com.ulises.features.events.list.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.components.RatingButton
import com.ulises.components.dialogs.AlertDialog
import com.ulises.features.events.list.R
import com.ulises.features.events.list.utils.RatingType
import com.ulises.features.events.list.utils.scheduleEventMockList
import com.ulises.theme.SleepScheduleTheme
import models.Kid
import models.ScheduledEvent

private val ratingValues = arrayOf(RatingType.Bad, RatingType.Neutral, RatingType.Good)

@Composable
fun DialogFeedback(
    event: ScheduledEvent?,
    kid: Kid?,
    onDismiss: () -> Unit,
    onUpdateRating: (Int) -> Unit,
) {
    var rateSelected by remember { mutableIntStateOf( kid?.rate ?: 0) }

    AlertDialog(onDismiss = onDismiss) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = stringResource(
                    id = R.string.dialog_rating_message,
                    kid?.name.orEmpty(),
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

@PreviewLightDark
@Composable
private fun PrevDialogFeedback() {
    SleepScheduleTheme {
        DialogFeedback(
            event = scheduleEventMockList[0],
            kid = Kid("Renata", 0),
            onDismiss = {},
            onUpdateRating = {}
        )
    }
}