package com.example.sleepschedule.ui.utils

import com.example.sleepschedule.R
import models.Birthday
import models.ScheduledEvent
import java.time.LocalDate

private val birthdays = setOf(
    Birthday(2, 15, "Ricardo"),
    Birthday(2, 28, "Yoyis"),
    Birthday(3, 3, "Renata"),
    Birthday(7, 28, "Ulises"),
    Birthday(7, 31, "Denisse"),
    Birthday(8, 25, "Lando")
)

fun ScheduledEvent.getImageFromMonth(): Int? {
    val date = LocalDate.parse(this.date)
    val birthdayCelebration = birthdays.find { it.month == date.monthValue && it.day == date.dayOfMonth }
    if (birthdayCelebration != null) {
        return R.drawable.img_birthday
    }
    return when (date.monthValue) {
        2 -> R.drawable.img_valentine
        3 -> R.drawable.img_spring
        8 -> R.drawable.img_summer
        9 -> R.drawable.img_mexico_flag
        10 -> R.drawable.img_halloween
        11 -> R.drawable.img_day_death
        12 -> R.drawable.img_christmas
        else -> null
    }
}