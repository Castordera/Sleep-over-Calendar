package com.example.sleepschedule.data.database

import com.example.sleepschedule.common.TimeHelper

data class ScheduledEvent(
    val id: String? = null,
    val date: Long? = null,
    val createdBy: String? = null,
    val createdOn: Long? = null,
    val rating: Int? = null
)

fun ScheduledEvent.toDomain(timeHelper: TimeHelper) = models.ScheduledEvent(
    id = id ?: "",
    date = date?.let { timeHelper.convertToLocalDateFromMillis(it) } ?: "",
    createdBy = createdBy ?: "",
    createdOn = createdOn?.let { timeHelper.convertToLocalDateFromMillis(it) } ?: "",
    rating = rating ?: 0,
    kidName = "Renata"
)