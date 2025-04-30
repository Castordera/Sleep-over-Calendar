package com.ulises.features.events.list.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.ulises.common.time.utils.TimeHelper
import com.ulises.features.events.list.R
import com.ulises.features.events.list.utils.getImageFromMonth
import com.ulises.features.events.list.utils.scheduleEventMockList
import com.ulises.theme.SleepScheduleTheme
import models.Kid
import models.ScheduledEvent

@Composable
fun ScheduleItem(
    modifier: Modifier = Modifier,
    item: ScheduledEvent,
    onClickUpdateFeedback: (Kid?) -> Unit,
    onClickDelete: () -> Unit,
    onClickItem: (ScheduledEvent) -> Unit,
    onClickEdit: (ScheduledEvent) -> Unit,
) {
    val image = remember { item.getImageFromMonth() }

    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.clickable { onClickItem(item) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                val date = TimeHelper.toHumanReadable(item.date).split(",")
                                appendLine(date[0].capitalize(Locale.current))
                                append(date[1])
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            fontSize = 12.sp,
                            text = stringResource(id = R.string.date_added_by, item.createdBy),
                            fontStyle = FontStyle.Italic,
                            maxLines = 1,
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            if (item.selectedKids.isNotEmpty()) {
                                items(item.selectedKids) { kid ->
                                    KidRate(
                                        name = kid.name,
                                        rating = kid.rate,
                                        onClickRate = { onClickUpdateFeedback(kid) },
                                    )
                                }
                            } else {// Legacy way
                                items(1) {
                                    KidRate(
                                        name = item.kidName,
                                        rating = item.rating,
                                        onClickRate = { onClickUpdateFeedback(null) },
                                    )
                                }
                            }
                        }
                    }
                    IconButton(onClick = onClickDelete) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
                if (image != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(image)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(110.dp)
                            .absoluteOffset(25.dp, 25.dp)
                    )
                }
            }
            AnimatedVisibility(
                visible = item.isExpanded,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(modifier = Modifier.fillMaxWidth())
                    Row {
                        Text(
                            text = stringResource(id = R.string.card_comments_title),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 16.dp),
                        )
                        IconButton(onClick = { onClickEdit(item) }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                            )
                        }
                    }
                    Text(
                        text = item.comments,
                        modifier = Modifier.padding(end = 16.dp),
                    )
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, fontScale = 1.5f)
@Composable
private fun PrevScheduleItem() {
    SleepScheduleTheme {
        ScheduleItem(
            item = scheduleEventMockList[0].copy(isExpanded = true),
            onClickUpdateFeedback = {},
            onClickDelete = {},
            onClickItem = {},
            onClickEdit = {},
        )
    }
}