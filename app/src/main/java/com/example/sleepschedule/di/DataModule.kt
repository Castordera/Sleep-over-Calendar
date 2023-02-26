package com.example.sleepschedule.di

import com.example.sleepschedule.data.database.FirebaseRemoteDataSource
import com.ulises.data.datasources.ScheduleRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindScheduleRemoteDataSource(dataSource: FirebaseRemoteDataSource): ScheduleRemoteDataSource
}