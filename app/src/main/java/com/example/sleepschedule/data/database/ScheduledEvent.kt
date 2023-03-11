package com.example.sleepschedule.data.database

import androidx.annotation.Keep

@Keep
data class ScheduledEvent(
    val id: String? = null,
    val date: String? = null,
    val createdBy: String? = null,
    val createdOn: String? = null,
    val rating: Int? = null,
    val comments: String? = null
)

fun ScheduledEvent.toDomain() = models.ScheduledEvent(
    id = id.orEmpty(),
    date = date.orEmpty(),
    createdBy = createdBy.orEmpty(),
    createdOn = date.orEmpty(),
    rating = rating ?: 0,
    kidName = "Renata",
    comments = comments.orEmpty()
)