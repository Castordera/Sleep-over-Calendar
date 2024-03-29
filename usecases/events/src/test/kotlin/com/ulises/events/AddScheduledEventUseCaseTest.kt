package com.ulises.events

import com.ulises.data.repositories.ScheduleRepository
import com.ulises.events.data.outcomeEvent
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
class AddScheduledEventUseCaseTest {

    @MockK
    private lateinit var repository: ScheduleRepository
    private lateinit var useCase: AddScheduledEventUseCase

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        useCase = AddScheduledEventUseCase(repository)
    }

    @Test
    fun `Attempt to invoke its method with no errors`() = runTest {
        coEvery { repository.addNewSchedule(any()) } just runs
        useCase(outcomeEvent)
        coVerify {
            repository.addNewSchedule(outcomeEvent)
        }
    }

    @Test
    fun `Attempt to add a new event but exception is thrown instead`() = runTest {
        val error = "Mock error message"
        coEvery { repository.addNewSchedule(any()) } throws Exception(error)
        runCatching {
            useCase(outcomeEvent)
        }.onSuccess {
            fail("This should not be reached, an error was expected")
        }.onFailure {
            assertEquals(it.message, error)
        }
        coVerify {
            repository.addNewSchedule(outcomeEvent)
        }
    }
}