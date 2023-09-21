package com.ulises.dispatcher_core

import kotlinx.coroutines.CoroutineDispatcher

interface ScheduleDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}