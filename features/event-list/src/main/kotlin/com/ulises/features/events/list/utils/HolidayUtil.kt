package com.ulises.features.events.list.utils

import com.ulises.features.events.list.R
import models.Holiday
import models.ScheduledEvent
import java.time.LocalDate

private val holidays = setOf(
    Holiday(2, 15),
    Holiday(2, 28),
    Holiday(3, 3),
    Holiday(7, 28),
    Holiday(7, 31),
    Holiday(8, 25),
)

fun ScheduledEvent.getImageFromMonth(): Int? {
    val date = LocalDate.parse(this.date)
    val birthdayCelebration = holidays.verifyBirthDays(date)
    if (birthdayCelebration != null) {
        return birthdayCelebration
    }
    return when (date.monthValue) {
        2 -> R.drawable.img_valentine
        3, 4, 5 -> R.drawable.img_spring
        6, 7, 8 -> R.drawable.img_summer
        9 -> R.drawable.img_mexico_flag
        10 -> R.drawable.img_halloween
        11 -> R.drawable.img_day_death
        12 -> R.drawable.img_christmas
        else -> null
    }
}

private fun Set<Holiday>.verifyBirthDays(date: LocalDate): Int? {
    return this.find { it.month == date.monthValue && it.day == date.dayOfMonth }
        ?.let { R.drawable.img_birthday }
}