package com.example.sleepschedule.ui.components

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
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
import com.example.sleepschedule.common.scheduleEventMockList
import com.ulises.theme.SleepScheduleTheme
import com.example.sleepschedule.ui.utils.getImageFromMonth
import models.CardFace
import models.ScheduledEvent

@Composable
fun ScheduleItem(
    modifier: Modifier = Modifier,
    item: ScheduledEvent,
    onClickUpdateFeedback: () -> Unit,
    onClickDelete: () -> Unit,
    onClickItem: (ScheduledEvent) -> Unit
) {
    var image by remember { mutableStateOf<Int?>(null) }
    val rotation: Float by animateFloatAsState(
        targetValue = if (item.cardFace == CardFace.BACK) 180f else 0f,
        label = "Rotation Card"
    )

    LaunchedEffect(key1 = Unit) {
        image = item.getImageFromMonth()
    }

    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            }
            .clickable { onClickItem(item) }
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd
        ) {
            if (item.cardFace == CardFace.FRONT) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 16.dp, bottom = 16.dp, start = 16.dp)
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
                    Row {
                        IconButton(
                            icon = R.drawable.ic_thumbs_up_down,
                            onClick = onClickUpdateFeedback,
                        )
                        IconButton(
                            icon = R.drawable.ic_delete,
                            onClick = onClickDelete,
                        )
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
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .graphicsLayer { rotationY = 180f }
                ) {
                    Text(
                        text = stringResource(id = R.string.card_comments_title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = item.comments)
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrevScheduleItem() {
    SleepScheduleTheme {
        ScheduleItem(
            item = scheduleEventMockList[0],
            onClickUpdateFeedback = {},
            onClickDelete = {},
            onClickItem = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrevScheduleItemBack() {
    SleepScheduleTheme {
        ScheduleItem(
            item = scheduleEventMockList[0].copy(cardFace = CardFace.BACK),
            onClickUpdateFeedback = {},
            onClickDelete = {},
            onClickItem = {}
        )
    }
}