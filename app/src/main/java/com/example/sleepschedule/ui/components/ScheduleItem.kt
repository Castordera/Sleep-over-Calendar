package com.example.sleepschedule.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sleepschedule.R
import com.example.sleepschedule.ui.theme.SleepScheduleTheme

@Composable
fun ScheduleItem(
    modifier: Modifier = Modifier,
    onClickUpdate: () -> Unit,
    onClickDelete: () -> Unit
) {
    Card {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Column {
                Text(
                    text = "24 de Enero, 2020"
                )
                Text(
                    fontSize = 8.sp,
                    text = stringResource(id = R.string.date_added_by, "Ricardo")
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
        ScheduleItem(onClickUpdate = {}, onClickDelete = {})
    }
}