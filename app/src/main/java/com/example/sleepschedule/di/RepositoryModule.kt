package com.example.sleepschedule.di

import com.ulises.data.repositories.*
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

    @Binds
    abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}