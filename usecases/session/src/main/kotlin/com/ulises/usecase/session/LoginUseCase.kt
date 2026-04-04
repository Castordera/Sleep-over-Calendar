package com.ulises.usecase.session

import com.ulises.data.repositories.SessionRepository
import com.ulises.data.repositories.UserRepository
import com.ulises.session.UserSessionManager
import models.User
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val userRepository: UserRepository,
    private val userSessionManager: UserSessionManager,
) {
    suspend operator fun invoke(email: String, password: String): User? {
        val firebaseUser = sessionRepository.signIn(email, password) ?: throw Exception("User not found")
        val user = userRepository.getCurrentUser(firebaseUser.id)
        userSessionManager.updateUserSessionState(user)
        return user
    }
}