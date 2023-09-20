package com.ulises.data.repositories

import models.User

interface UserRepository {
    suspend fun createNewUser(user: User)
    suspend fun getCurrentUser(): User
}