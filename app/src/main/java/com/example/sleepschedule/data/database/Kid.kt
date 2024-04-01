package com.example.sleepschedule.data.database

import androidx.annotation.Keep

@Keep
data class Kid(
    val name: String? = null,
    val rate: Int? = null,
)

fun Kid.toDomain() = models.Kid(
    name = name.orEmpty(),
    rate = rate ?: 0,
)
