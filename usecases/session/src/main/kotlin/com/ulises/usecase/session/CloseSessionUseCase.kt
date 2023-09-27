package com.ulises.usecase.session

import com.ulises.data.repositories.SessionRepository
import javax.inject.Inject

class CloseSessionUseCase @Inject constructor(
    private val repository: SessionRepository
) {
    suspend operator fun invoke() {
        repository.signOut()
    }
}