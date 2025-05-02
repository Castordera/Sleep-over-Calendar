package com.ulises.events

import com.ulises.data.repositories.ScheduleRepository
import models.ScheduledEvent
import javax.inject.Inject

class GetScheduledEventUseCase @Inject constructor(
    private val repository: ScheduleRepository,
) {
    suspend operator fun invoke(eventId: String): ScheduledEvent {
        return repository.getEvent(eventId)
    }
}