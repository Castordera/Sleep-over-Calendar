package com.example.sleepschedule.di

import com.ulises.dispatcher_core.ScheduleDispatchers
import com.ulises.dispatcher_core.ScheduleDispatchersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DispatchersModule {

    @Binds
    @Singleton
    fun bindDispatchers(imp: ScheduleDispatchersImpl): ScheduleDispatchers
}