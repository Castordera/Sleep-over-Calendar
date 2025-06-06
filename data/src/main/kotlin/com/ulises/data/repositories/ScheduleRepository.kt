package com.ulises.data.repositories

import kotlinx.coroutines.flow.Flow
import models.ScheduledEvent
import outcomes.OutcomeScheduledEvent

interface ScheduleRepository {
    fun getAllScheduledEvents(): Flow<List<ScheduledEvent>>
    suspend fun addNewSchedule(event: OutcomeScheduledEvent)
    suspend fun deleteScheduleEvent(eventId: String)
    suspend fun updateScheduleEventRating(eventId: String, newRating: Int)
    suspend fun updateScheduleEventRating(eventId: String, newRate: Int, index: Int)
    suspend fun getEvent(eventId: String): ScheduledEvent
    suspend fun updateEvent(data: OutcomeScheduledEvent)
}