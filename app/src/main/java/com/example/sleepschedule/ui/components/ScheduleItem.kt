package com.example.sleepschedule.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.sleepschedule.R
import com.example.sleepschedule.common.TimeHelper
import com.example.sleepschedule.ui.theme.SleepScheduleTheme
import com.example.sleepschedule.ui.utils.demoEvent
import com.example.sleepschedule.ui.utils.getImageFromMonth
import models.ScheduledEvent

@Composable
fun ScheduleItem(
    modifier: Modifier = Modifier,
    item: ScheduledEvent,
    onClickUpdateFeedback: () -> Unit,
    onClickDelete: () -> Unit
) {
    var image by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(key1 = Unit) {
        image = item.getImageFromMonth()
    }

    Card(
        shape = RoundedCornerShape(15.dp)
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            val date = TimeHelper.convertToHumanReadable(item.date).split(",")
                            appendLine(date[0].capitalize(Locale.current))
                            append(date[1])
                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        fontSize = 12.sp,
                        text = stringResource(id = R.string.date_added_by, item.createdBy),
                        fontStyle = FontStyle.Italic
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        items(1) {
                            KidRate(
                                name = item.kidName,
                                rating = item.rating
                            )
                        }
                    }
                }
                ButtonIcon(image = R.drawable.ic_thumbs_up_down) {
                    onClickUpdateFeedback()
                }
                ButtonIcon(image = R.drawable.ic_delete) {
                    onClickDelete()
                }
            }
            if (image != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image!!)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .absoluteOffset(25.dp, 25.dp)
                )
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
            item = demoEvent,
            onClickUpdateFeedback = {},
            onClickDelete = {}
        )
    }
}