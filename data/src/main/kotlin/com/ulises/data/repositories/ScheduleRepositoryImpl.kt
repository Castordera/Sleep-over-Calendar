package com.ulises.data.repositories

import com.ulises.data.datasources.ScheduleRemoteDataSource
import kotlinx.coroutines.flow.Flow
import models.ScheduledEvent
import outcomes.OutcomeScheduledEvent
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val remoteDataSource: ScheduleRemoteDataSource
) : ScheduleRepository {

    override suspend fun addNewSchedule(event: OutcomeScheduledEvent) {
        remoteDataSource.addNewScheduleEvent(event)
    }

    override fun getAllScheduledEvents(): Flow<List<ScheduledEvent>> {
        return remoteDataSource.getAllScheduledEvents()
    }

    override suspend fun deleteScheduleEvent(eventId: String) {
        remoteDataSource.deleteScheduleEvent(eventId)
    }

    override suspend fun updateScheduleEventRating(eventId: String, newRating: Int) {
        remoteDataSource.updateScheduleEventRating(eventId, newRating)
    }

    override suspend fun updateScheduleEventRating(eventId: String, newRate: Int, index: Int) {
        remoteDataSource.updateScheduleEventRating(eventId, newRate, index)
    }
}