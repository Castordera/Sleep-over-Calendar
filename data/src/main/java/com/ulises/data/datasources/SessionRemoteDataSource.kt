package com.ulises.data.datasources

import models.User

interface SessionRemoteDataSource {
    suspend fun getCurrentUser(): User?
    suspend fun signInWithEmailPassword(email: String, password: String): User?
    suspend fun registerUser(email: String, password: String): User?
    suspend fun closeSession()
}