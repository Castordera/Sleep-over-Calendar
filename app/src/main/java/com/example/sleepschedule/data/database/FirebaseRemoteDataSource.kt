package com.example.sleepschedule.data.database

import android.util.Log
import com.example.sleepschedule.di.AppDispatchers
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.ulises.data.datasources.ScheduleRemoteDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.tasks.await
import outcomes.OutcomeScheduledEvent
import javax.inject.Inject
import kotlin.collections.map
import kotlin.collections.toList

class FirebaseRemoteDataSource @Inject constructor (
    private val database: DatabaseReference,
    private val dispatchers: AppDispatchers
): ScheduleRemoteDataSource {

    override fun getAllScheduledEvents(): Flow<List<models.ScheduledEvent>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(TAG, "${snapshot.value}")
                val mapResponse = snapshot.getValue<Map<String, ScheduledEvent>>() ?: emptyMap()
                trySend(mapResponse)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, error.message)
            }
        }
        database.orderByChild("/date").addValueEventListener(listener)
        awaitClose {
            Log.d(TAG, "Stop getting values")
            database.removeEventListener(listener)
        }
    }.transform {map ->
        emit(map.values.toList().sortedBy { it.date })
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

    private companion object {
        const val TAG = "FirebaseRemoteDataSource"
        const val KEY_RATING = "rating"
    }
}