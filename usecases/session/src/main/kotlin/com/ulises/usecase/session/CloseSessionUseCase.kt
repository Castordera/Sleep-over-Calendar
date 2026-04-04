package com.ulises.usecase.session

import com.ulises.data.repositories.SessionRepository
import com.ulises.session.UserSessionManager
import javax.inject.Inject

class CloseSessionUseCase @Inject constructor(
    private val repository: SessionRepository,
    private val userSessionManager: UserSessionManager,
) {
    suspend operator fun invoke() {
        repository.signOut()
        userSessionManager.updateUserSessionState(null)
    }
}