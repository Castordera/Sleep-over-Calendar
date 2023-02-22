package com.example.sleepschedule.common

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

const val TIME_PATTERN_HUMAN_READABLE = "EEEE dd/MM/yyyy"

class TimeHelper @Inject constructor() {

    fun convertToLocalDateFromMillis(time: Long): String {
        val date = LocalDate.ofEpochDay(Duration.ofMillis(time).toDays())
        val formatter = DateTimeFormatter.ofPattern(TIME_PATTERN_HUMAN_READABLE)
        return date.format(formatter).capitalize(Locale.current)
    }
}