package com.ulises.data.repositories

import kotlinx.coroutines.flow.Flow
import models.ScheduledEvent
import outcomes.OutcomeScheduledEvent

interface ScheduleRepository {
    fun getAllScheduledEvents(): Flow<List<ScheduledEvent>>
    suspend fun addNewSchedule(event: OutcomeScheduledEvent)
}