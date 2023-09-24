package com.ulises.data.datasources

import kotlinx.coroutines.flow.Flow
import models.User

interface UsersRemoteDataSource {
    suspend fun createUser(user: User)
    fun getCurrentUserFlow(userId: String): Flow<User>
    suspend fun getCurrentUser(userId: String): User
}