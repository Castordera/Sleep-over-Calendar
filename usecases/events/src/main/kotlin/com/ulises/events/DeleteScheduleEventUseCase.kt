package com.ulises.events

import com.ulises.data.repositories.ScheduleRepository
import javax.inject.Inject

class DeleteScheduleEventUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(eventId: String) {
        repository.deleteScheduleEvent(eventId)
    }
}