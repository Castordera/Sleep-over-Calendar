package com.example.sleepschedule.data.session

import com.google.firebase.auth.FirebaseAuth
import com.ulises.data.datasources.SessionRemoteDataSource
import kotlinx.coroutines.suspendCancellableCoroutine
import models.User
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resumeWithException

class FirebaseSessionDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : SessionRemoteDataSource {

    override suspend fun getCurrentUser(): User? {
        return firebaseAuth.currentUser?.toUser()
    }

    override suspend fun signInWithEmailPassword(email: String, password: String) = suspendCancellableCoroutine { continuation ->
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(firebaseAuth.currentUser?.toUser()) {
                        continuation.resumeWithException(it)
                    }
                } else {
                    continuation.resumeWithException(task.exception!!)
                }
            }
    }

    override suspend fun registerUser(email: String, password: String): User? = suspendCancellableCoroutine { continuation ->
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(firebaseAuth.currentUser?.toUser()) {
                        Timber.e("Error getting user", it)
                        continuation.resumeWithException(it)
                    }
                } else {
                    Timber.e("Error getting user", task.exception)
                    continuation.resumeWithException(task.exception!!)
                }
            }
    }

    override suspend fun reEnterCredentials(): User? {
        TODO("Not yet implemented")
    }
}