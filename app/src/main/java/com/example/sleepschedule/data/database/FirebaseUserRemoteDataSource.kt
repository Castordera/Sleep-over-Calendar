package com.example.sleepschedule.data.database

import com.example.sleepschedule.di.FirebaseUsersReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.ulises.data.datasources.UsersRemoteDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import models.User as DomainUser
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseUserRemoteDataSource @Inject constructor(
    @FirebaseUsersReference private val database: DatabaseReference
) : UsersRemoteDataSource {

    override suspend fun createUser(user: DomainUser) {
        database.child(user.id).setValue(user).await()
    }

    override fun getCurrentUserFlow(userId: String) = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<User>()
                trySend(user!!.toDomain())
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.d("Get user flow cancelled")
            }
        }
        database.child(userId).addValueEventListener(listener)
        awaitClose { database.child(userId).removeEventListener(listener) }
    }

    override suspend fun getCurrentUser(userId: String): DomainUser = suspendCancellableCoroutine { continuation ->
        database.child(userId).get().addOnSuccessListener {
            val user = it.getValue<User>()
            continuation.resume(user!!.toDomain())
        }.addOnFailureListener {
            continuation.resumeWithException(it)
        }
    }
}