package com.ulises.events

import com.ulises.data.repositories.ScheduleRepository
import outcomes.OutcomeScheduledEvent
import javax.inject.Inject

class UpdateEventUseCase @Inject constructor(
    private val repository: ScheduleRepository,
) {
    suspend operator fun invoke(data: OutcomeScheduledEvent) {
        repository.updateEvent(data)
    }
}