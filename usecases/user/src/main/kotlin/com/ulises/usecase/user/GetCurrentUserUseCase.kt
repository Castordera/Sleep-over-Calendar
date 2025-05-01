package com.ulises.usecase.user

import com.ulises.data.repositories.SessionRepository
import com.ulises.data.repositories.UserRepository
import models.User
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): User? {
        val userSession = sessionRepository.getCurrentUser()
        if (userSession != null) {
            val userRemote = userRepository.getCurrentUser(userSession.id)
            return userRemote
        }
        return userSession
    }
}