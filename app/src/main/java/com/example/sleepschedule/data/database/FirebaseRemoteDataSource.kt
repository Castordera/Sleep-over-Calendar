package com.example.sleepschedule.data.database

import com.example.sleepschedule.di.FirebaseEventsReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.ulises.data.datasources.ScheduleRemoteDataSource
import com.ulises.dispatcher_core.ScheduleDispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.tasks.await
import outcomes.OutcomeScheduledEvent
import timber.log.Timber
import javax.inject.Inject

class FirebaseRemoteDataSource @Inject constructor (
    @FirebaseEventsReference private val database: DatabaseReference,
    private val dispatchers: ScheduleDispatchers
): ScheduleRemoteDataSource {

    override fun getAllScheduledEvents(): Flow<List<models.ScheduledEvent>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val mapResponse = snapshot.getValue<Map<String, ScheduledEvent>>() ?: emptyMap()
                Timber.d("Data retrieved: $mapResponse")
                trySend(mapResponse)
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.e("Error updating events", error)
            }
        }
        database.addValueEventListener(listener)
        awaitClose {
            database.removeEventListener(listener)
        }
    }.transform {map ->
        emit(map.values.toList().sortedByDescending { it.date })
    }.transform { orderedList ->
        emit(orderedList.map { it.toDomain() })
    }.flowOn(dispatchers.default)

    override suspend fun addNewScheduleEvent(event: OutcomeScheduledEvent) {
        database.child(event.id).setValue(event).await()
    }

    override suspend fun deleteScheduleEvent(eventId: String) {
        database.child(eventId).setValue(null).await()
    }

    override suspend fun updateScheduleEventRating(eventId: String, newRating: Int) {
        database.child(eventId).child(KEY_RATING).setValue(newRating).await()
    }

    override suspend fun updateScheduleEventRating(eventId: String, newRate: Int, index: Int) {
        database.child(eventId).child(KEY_SELECTED_KIDS).child(index.toString()).child(KEY_KID_RATING).setValue(newRate).await()
    }

    override suspend fun getEvent(eventId: String): models.ScheduledEvent {
        return database.child(eventId).get().await().getValue<ScheduledEvent>()!!.toDomain()
    }

    override suspend fun updateEvent(event: OutcomeScheduledEvent) {
        database.child(event.id).setValue(event).await()
    }

    private companion object {
        const val KEY_RATING = "rating"
        const val KEY_SELECTED_KIDS = "selectedKids"
        const val KEY_KID_RATING = "rate"
    }
}