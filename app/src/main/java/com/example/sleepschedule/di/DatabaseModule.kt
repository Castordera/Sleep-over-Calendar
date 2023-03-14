package com.example.sleepschedule.di

import com.example.sleepschedule.BuildConfig
import com.example.sleepschedule.data.utils.DATABASE_SECTION_NAME_SCHEDULE
import com.example.sleepschedule.data.utils.DATABASE_SECTION_NAME_SCHEDULE_DEBUG
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    @DatabaseUrl
    fun provideDataBaseUrl(): String {
        return if (!BuildConfig.DEBUG) {
            DATABASE_SECTION_NAME_SCHEDULE
        } else {
            DATABASE_SECTION_NAME_SCHEDULE_DEBUG
        }
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabase(
        @DatabaseUrl databaseUrl: String
    ) = Firebase.database.reference.child(databaseUrl)
}