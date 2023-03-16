package com.example.sleepschedule.data.session

import com.google.firebase.auth.FirebaseUser
import models.User

fun FirebaseUser.toUser(): User {
    return User(
        id = uid,
        email = email.orEmpty()
    )
}