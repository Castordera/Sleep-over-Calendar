package com.ulises.events

import com.ulises.data.repositories.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import models.ScheduledEvent
import javax.inject.Inject

class GetAllScheduledEventsUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {

    operator fun invoke(): Flow<List<ScheduledEvent>> {
        return repository.getAllScheduledEvents()
    }
}