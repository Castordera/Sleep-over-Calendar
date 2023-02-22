package com.ulises.data.datasources

import kotlinx.coroutines.flow.Flow
import models.ScheduledEvent
import outcomes.OutcomeScheduledEvent

interface ScheduleRemoteDataSource {
    fun getAllScheduledEvents(): Flow<List<ScheduledEvent>>
    suspend fun addNewScheduleEvent(event: OutcomeScheduledEvent)
}