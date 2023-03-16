package com.ulises.data.di

import com.ulises.data.repositories.ScheduleRepository
import com.ulises.data.repositories.ScheduleRepositoryImpl
import com.ulises.data.repositories.SessionRepository
import com.ulises.data.repositories.SessionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindScheduleRepository(repository: ScheduleRepositoryImpl): ScheduleRepository

    @Binds
    abstract fun bindSessionRepository(repository: SessionRepositoryImpl): SessionRepository
}