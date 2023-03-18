package com.example.sleepschedule.di

import com.example.sleepschedule.data.database.FirebaseRemoteDataSource
import com.example.sleepschedule.data.database.FirebaseUserRemoteDataSource
import com.example.sleepschedule.data.session.FirebaseSessionDataSource
import com.ulises.data.datasources.ScheduleRemoteDataSource
import com.ulises.data.datasources.SessionRemoteDataSource
import com.ulises.data.datasources.UsersRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindScheduleRemoteDataSource(dataSource: FirebaseRemoteDataSource): ScheduleRemoteDataSource

    @Binds
    abstract fun bindSessionRemoteDataSource(sessionSource: FirebaseSessionDataSource): SessionRemoteDataSource

    @Binds
    abstract fun bindUsersRemoteDataSource(usersSource: FirebaseUserRemoteDataSource): UsersRemoteDataSource
}