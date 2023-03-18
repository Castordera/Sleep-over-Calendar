package com.ulises.usecases.users

import com.ulises.data.repositories.UserRepository
import models.User
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        repository.createNewUser(user)
    }
}