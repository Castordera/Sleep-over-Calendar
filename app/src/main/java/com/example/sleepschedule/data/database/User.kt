package com.example.sleepschedule.data.database

import androidx.annotation.Keep

@Keep
data class User(
    val id: String? = null,
    val email: String? = null,
    val name: String? = null
)

fun User.toDomain() = models.User(
    id = id.orEmpty(),
    email = email.orEmpty(),
    name = name.orEmpty()
)