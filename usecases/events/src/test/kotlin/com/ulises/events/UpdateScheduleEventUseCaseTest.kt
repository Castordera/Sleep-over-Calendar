package com.ulises.events

import com.ulises.data.repositories.ScheduleRepository
import com.ulises.test_core.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode

@ExtendWith(CoroutineTestRule::class)
@Execution(ExecutionMode.CONCURRENT)
class UpdateScheduleEventUseCaseTest {

    @MockK
    private lateinit var repository: ScheduleRepository
    private lateinit var usecase: UpdateScheduleEventUseCase

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        usecase = UpdateScheduleEventUseCase(repository)
    }

    @Test
    fun `Attempt to update an event with no errors`() = runTest {
        val eventId = "123"
        val newRating = 1
        coEvery { repository.updateScheduleEventRating(any(), any()) } just runs
        usecase(eventId, newRating)
        coVerify { repository.updateScheduleEventRating(eventId, newRating) }
    }

    @Test
    fun `Attempt to update an event but exception is thrown`() = runTest {
        val eventId = "123"
        val newRating = 1
        val errorMessage = "Error message"
        coEvery { repository.updateScheduleEventRating(any(), any()) } throws Exception(errorMessage)
        runCatching {
            usecase(eventId, newRating)
        }.onSuccess {
            fail("An exception was expected")
        }.onFailure {
            assertEquals(it.message, errorMessage)
        }
        coVerify { repository.updateScheduleEventRating(eventId, newRating) }
    }
}