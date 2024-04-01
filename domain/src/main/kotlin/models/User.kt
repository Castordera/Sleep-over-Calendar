package models

import androidx.annotation.Keep

@Keep
data class User(
    val id: String,
    val email: String,
    val name: String
)
