package com.ulises.data.datasources

import kotlinx.coroutines.flow.Flow
import models.ScheduledEvent
import outcomes.OutcomeScheduledEvent

interface ScheduleRemoteDataSource {
    fun getAllScheduledEvents(): Flow<List<ScheduledEvent>>
    suspend fun addNewScheduleEvent(event: OutcomeScheduledEvent)
    suspend fun deleteScheduleEvent(eventId: String)
    suspend fun updateScheduleEventRating(eventId: String, newRating: Int)
    suspend fun updateScheduleEventRating(eventId: String, newRate: Int, index: Int)
    suspend fun getEvent(eventId: String): ScheduledEvent
    suspend fun updateEvent(event: OutcomeScheduledEvent)
}