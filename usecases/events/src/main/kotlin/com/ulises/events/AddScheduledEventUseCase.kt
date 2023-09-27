package com.ulises.events

import com.ulises.data.repositories.ScheduleRepository
import outcomes.OutcomeScheduledEvent
import javax.inject.Inject

class AddScheduledEventUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(event: OutcomeScheduledEvent) {
        repository.addNewSchedule(event)
    }
}