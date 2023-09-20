package com.ulises.data.repositories

import com.ulises.data.datasources.UsersRemoteDataSource
import models.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UsersRemoteDataSource
) : UserRepository {
    override suspend fun createNewUser(user: User) {
        remoteDataSource.createUser(user)
    }

    override suspend fun getCurrentUser(): User {
        TODO("Not yet implemented")
    }
}