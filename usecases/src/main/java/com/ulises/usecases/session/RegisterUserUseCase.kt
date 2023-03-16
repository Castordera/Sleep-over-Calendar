package com.ulises.usecases.session

import com.ulises.data.repositories.SessionRepository
import models.User
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: SessionRepository
) {
    suspend operator fun invoke(email: String, password: String): User? {
        return repository.registerUser(email, password)
    }
}