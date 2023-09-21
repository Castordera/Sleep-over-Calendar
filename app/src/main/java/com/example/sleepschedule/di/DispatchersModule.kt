package com.example.sleepschedule.di

import com.ulises.dispatcher_core.ScheduleDispatchersImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DispatchersModule {

    @Binds
    @Singleton
    fun bindDispatchers(imp: ScheduleDispatchersImpl): com.ulises.dispatcher_core.ScheduleDispatchers
    //Todo(Remove other dispatcher)

    companion object {
        @Provides
        @Singleton
        fun provideDispatchers() = AppDispatchers(
            main = Dispatchers.Main,
            io = Dispatchers.IO,
            default = Dispatchers.Default
        )
    }
}

class AppDispatchers(
    override val main: CoroutineDispatcher,
    override val io: CoroutineDispatcher,
    override val default: CoroutineDispatcher
): ScheduleDispatchers

interface ScheduleDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}