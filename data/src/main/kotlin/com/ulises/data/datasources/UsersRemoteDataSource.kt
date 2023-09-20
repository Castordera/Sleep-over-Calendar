package com.ulises.data.datasources

import models.User

interface UsersRemoteDataSource {
    suspend fun createUser(user: User)
    suspend fun getCurrentUser()
}