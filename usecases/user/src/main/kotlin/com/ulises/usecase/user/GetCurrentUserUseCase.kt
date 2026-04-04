package com.ulises.usecase.user

import com.ulises.data.repositories.SessionRepository
import com.ulises.data.repositories.UserRepository
import com.ulises.session.UserSessionManager
import models.User
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val userRepository: UserRepository,
    private val userSessionManager: UserSessionManager,
) {
    suspend operator fun invoke(): User {
        val userSession = sessionRepository.getCurrentUser()
        if (userSession != null) {
            val userRemote = userRepository.getCurrentUser(userSession.id)
            userSessionManager.updateUserSessionState(userRemote)
            return userRemote
        }
        throw Exception("User not found")
    }
}