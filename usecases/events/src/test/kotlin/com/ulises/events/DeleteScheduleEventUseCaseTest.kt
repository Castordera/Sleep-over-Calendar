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
class DeleteScheduleEventUseCaseTest {

    @MockK
    private lateinit var repository: ScheduleRepository
    private lateinit var useCase: DeleteScheduleEventUseCase

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        useCase = DeleteScheduleEventUseCase(repository)
    }

    @Test
    fun `Attempt to delete an event with no errors`() = runTest {
        val eventId = "123456789"
        coEvery { repository.deleteScheduleEvent(any()) } just runs
        useCase(eventId)
        coVerify { repository.deleteScheduleEvent(eventId) }
    }

    @Test
    fun `Attempt to delete an event but an error is thrown`() = runTest {
        val eventId = "123456789"
        val errorMessage = "Demo error message"
        coEvery { repository.deleteScheduleEvent(any()) } throws Exception(errorMessage)
        runCatching {
            useCase(eventId)
        }.onSuccess {
            fail("An exception was expected")
        }.onFailure {
            assertEquals(it.message, errorMessage)
        }
        coVerify { repository.deleteScheduleEvent(eventId) }
    }
}