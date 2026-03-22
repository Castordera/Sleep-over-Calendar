package com.example.sleepschedule.data.database

import androidx.annotation.Keep
import models.Attendee

@Keep
data class Kid(
    val name: String? = null,
    val rate: Int? = null,
)

fun Kid.toDomain() = models.Kid(
    name = name.orEmpty(),
    rate = rate ?: 0,
)

fun Kid.toDomainV2() = Attendee(
    name = name.orEmpty(),
    mood = rate.getMood(),
)

internal fun Int?.getMood(): Attendee.Mood = when (this) {
    -1 -> Attendee.Mood.BAD
    1 -> Attendee.Mood.GOOD
    else -> Attendee.Mood.NEUTRAL
}
