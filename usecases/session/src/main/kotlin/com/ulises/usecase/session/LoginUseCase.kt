package com.ulises.usecase.session

import com.ulises.data.repositories.SessionRepository
import models.User
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(email: String, password: String): User? {
        return sessionRepository.signIn(email, password)
    }
}