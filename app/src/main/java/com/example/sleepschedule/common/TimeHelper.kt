package com.example.sleepschedule.common

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object TimeHelper {

    private const val TIME_PATTERN_HUMAN_READABLE = "EEEE dd/MM/yyyy"
    private val humanReadableFormat = DateTimeFormatter.ofPattern(TIME_PATTERN_HUMAN_READABLE)

    fun convertRawToFormattedString(year: Int, month: Int, day: Int): String {
        return LocalDate.of(year, month, day).format(DateTimeFormatter.ISO_DATE)
    }

    fun convertToHumanReadable(dateString: String): String {
        return LocalDate.parse(dateString).format(humanReadableFormat)
    }
}