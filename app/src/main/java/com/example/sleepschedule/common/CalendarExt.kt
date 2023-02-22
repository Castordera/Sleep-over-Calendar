package com.example.sleepschedule.common

import java.util.*

val Calendar.year: Int
    get() = this.get(Calendar.YEAR)

val Calendar.month: Int
    get() = this.get(Calendar.MONTH)

val Calendar.day: Int
    get() = this.get(Calendar.DAY_OF_MONTH)

fun Calendar.getDateFromRaw(year: Int, month: Int, day: Int): Long {
    this.set(year, month, day)
    return this.timeInMillis
}