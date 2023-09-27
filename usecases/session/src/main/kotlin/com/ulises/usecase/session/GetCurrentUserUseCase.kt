package com.ulises.usecase.session

import com.ulises.data.repositories.SessionRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke() = sessionRepository.getCurrentUser()
}