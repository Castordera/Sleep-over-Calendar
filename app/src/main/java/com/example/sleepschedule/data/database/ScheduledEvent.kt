package com.example.sleepschedule.data.database

data class ScheduledEvent(
    val id: String? = null,
    val date: Long? = null,
    val createdBy: String? = null,
    val createdOn: Long? = null,
    val rating: Int? = null,
    val comments: String? = null
)

fun ScheduledEvent.toDomain() = models.ScheduledEvent(
    id = id.orEmpty(),
    date = date ?: 0L,//?.let { timeHelper.convertToLocalDateFromMillis(it) } ?: "",
    createdBy = createdBy.orEmpty(),
    createdOn = createdOn ?: 0L,//?.let { timeHelper.convertToLocalDateFromMillis(it) } ?: "",
    rating = rating ?: 0,
    kidName = "Renata",
    comments = comments.orEmpty()
)