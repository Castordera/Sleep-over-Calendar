package com.example.sleepschedule.di

import com.example.sleepschedule.data.utils.DATABASE_SECTION_NAME_SCHEDULE
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
    fun provideFirebaseDatabase() = Firebase.database.reference.child(DATABASE_SECTION_NAME_SCHEDULE)
}