package com.ulises.events

import app.cash.turbine.test
import com.ulises.data.repositories.ScheduleRepository
import com.ulises.events.data.event
import com.ulises.test_core.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode


@ExtendWith(CoroutineTestRule::class)
@Execution(ExecutionMode.CONCURRENT)
class GetAllScheduledEventsUseCaseTest {

    @MockK
    private lateinit var repository: ScheduleRepository
    private lateinit var usecase: GetAllScheduledEventsUseCase

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        usecase = GetAllScheduledEventsUseCase(repository)
    }

    @Test
    fun `Attempt to get a list of events with no errors`() = runTest {
        val eventsList = listOf(event)
        coEvery { repository.getAllScheduledEvents() } returns flowOf(eventsList)
        usecase().test {
            assertEquals(eventsList, awaitItem())
            awaitComplete()
            ensureAllEventsConsumed()
        }
        coVerify { repository.getAllScheduledEvents() }
    }
}