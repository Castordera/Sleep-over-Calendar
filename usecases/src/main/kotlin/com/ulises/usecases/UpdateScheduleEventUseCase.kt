package com.ulises.usecases

import com.ulises.data.repositories.ScheduleRepository
import javax.inject.Inject

class UpdateScheduleEventUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(eventId: String, newRating: Int) {
        repository.updateScheduleEventRating(eventId, newRating)
    }
}