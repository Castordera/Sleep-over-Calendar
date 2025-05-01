package com.ulises.test_core

import com.ulises.dispatcher_core.ScheduleDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher

class TestDispatchers(
    private val dispatchers: TestDispatcher = StandardTestDispatcher()
): ScheduleDispatchers {
    override val main: CoroutineDispatcher
        get() = dispatchers
    override val io: CoroutineDispatcher
        get() = dispatchers
    override val default: CoroutineDispatcher
        get() = dispatchers
}