package com.ulises.data.repositories

import models.User

interface SessionRepository {
    suspend fun getCurrentUser(): User?
    suspend fun signIn(email: String, password: String): User?
    suspend fun registerUser(email: String, password: String): User?
}