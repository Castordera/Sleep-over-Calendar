package com.example.sleepschedule.data.database

import com.example.sleepschedule.di.FirebaseUsersReference
import com.google.firebase.database.DatabaseReference
import com.ulises.data.datasources.UsersRemoteDataSource
import kotlinx.coroutines.tasks.await
import models.User
import javax.inject.Inject

class FirebaseUserRemoteDataSource @Inject constructor(
    @FirebaseUsersReference private val database: DatabaseReference
) : UsersRemoteDataSource {

    override suspend fun createUser(user: User) {
        database.child(user.id).setValue(user).await()
    }

    override suspend fun getCurrentUser() {

    }
}