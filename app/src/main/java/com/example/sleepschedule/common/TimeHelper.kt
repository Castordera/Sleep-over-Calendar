package com.example.sleepschedule.common

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object TimeHelper {

    private const val TIME_PATTERN_HUMAN_READABLE = "EEEE,dd 'de' MMMM 'del' yyyy"
    private const val TIME_ZONE = "UTC"
    private val humanReadableFormat = DateTimeFormatter.ofPattern(TIME_PATTERN_HUMAN_READABLE)

    fun toHumanReadable(dateString: String): String {
        return LocalDate.parse(dateString).toHumanReadable()
    }

    fun LocalDate.toHumanReadable(format: DateTimeFormatter = humanReadableFormat): String {
        return this.format(format)
    }

    fun LocalDate.toMillis(): Long {
        return atStartOfDay(ZoneId.of(TIME_ZONE)).toInstant().toEpochMilli()
    }

    fun Long.toLocalDate(): LocalDate {
        return Instant.ofEpochMilli(this).atZone(ZoneId.of(TIME_ZONE)).toLocalDate()
    }

    fun LocalDate.toISODate(): String {
        return this.format(DateTimeFormatter.ISO_DATE)
    }
}