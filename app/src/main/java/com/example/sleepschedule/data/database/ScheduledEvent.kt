package com.example.sleepschedule.data.database

import androidx.annotation.Keep
import models.Attendee
import java.time.LocalDate

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
    comments = comments.orEmpty(),
    selectedKids = mapToKidList(),
    attendees = mapAttendees(),
    isLegacy = selectedKids == null,
    dateScheduled = date.toLocalDate(),
)

private fun ScheduledEvent.mapToKidList(): List<models.Kid> {
    return selectedKids?.map { it.toDomain() } ?: listOf(models.Kid(kidName.orEmpty(), rating ?: 0))
}

private fun ScheduledEvent.mapAttendees(): List<Attendee> {
    return selectedKids?.map { it.toDomainV2() } ?: listOf(
        Attendee(
            kidName.orEmpty(),
            rating.getMood()
        )
    )
}

private fun String?.toLocalDate(): LocalDate {
    return if (this != null) {
        LocalDate.parse(this)
    } else {
        LocalDate.now()
    }
}