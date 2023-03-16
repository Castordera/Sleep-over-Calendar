package com.ulises.data.repositories

import com.ulises.data.datasources.SessionRemoteDataSource
import models.User
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sessionRemoteDataSource: SessionRemoteDataSource
) : SessionRepository {

    override suspend fun getCurrentUser() = sessionRemoteDataSource.getCurrentUser()

    override suspend fun signIn(email: String, password: String): User? {
        return sessionRemoteDataSource.signInWithEmailPassword(email, password)
    }

    override suspend fun registerUser(email: String, password: String): User? {
        return sessionRemoteDataSource.registerUser(email, password)
    }
}