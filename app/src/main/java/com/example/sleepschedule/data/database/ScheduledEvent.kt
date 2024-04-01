package com.example.sleepschedule.data.database

import androidx.annotation.Keep

@Keep
data class ScheduledEvent(
    val id: String? = null,
    val date: String? = null,
    val createdBy: String? = null,
    val createdOn: String? = null,
    val rating: Int? = null,
    val comments: String? = null,
    val kidName: String? = null,
    val selectedKids: List<Kid>? = null,
)

fun ScheduledEvent.toDomain() = models.ScheduledEvent(
    id = id.orEmpty(),
    date = date.orEmpty(),
    createdBy = createdBy.orEmpty(),
    createdOn = date.orEmpty(),
    rating = rating ?: 0,
    kidName = kidName.orEmpty(),
    comments = comments.orEmpty(),
    selectedKids = selectedKids?.map { it.toDomain() } ?: emptyList(),
)