package com.example.sleepschedule.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sleepschedule.R
import com.example.sleepschedule.ui.theme.SleepScheduleTheme
import models.ScheduledEvent

@Composable
fun ScheduleItem(
    modifier: Modifier = Modifier,
    item: ScheduledEvent,
    onClickUpdate: () -> Unit,
    onClickDelete: () -> Unit
) {
    Card {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    fontSize = 12.sp,
                    text = stringResource(id = R.string.date_added_by, item.createdBy),
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = item.date,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(imageResource = R.drawable.ic_edit) {
                onClickUpdate()
            }
            IconButton(imageResource = R.drawable.ic_delete) {
                onClickDelete()
            }
        }
    }
}

@Preview
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Prev_ScheduleItem() {
    SleepScheduleTheme {
        ScheduleItem(
            item = ScheduledEvent(
                id = "123",
                date = "24/02/2023",
                createdBy = "Ulises",
                createdOn = "24/02/2023",
                rating = 0
            ),
            onClickUpdate = {},
            onClickDelete = {}
        )
    }
}