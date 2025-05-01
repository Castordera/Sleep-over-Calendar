package com.ulises.features.event.add.ui

import com.ulises.events.AddScheduledEventUseCase
import com.ulises.session.UserSessionManager
import com.ulises.test_core.CoroutineTestRule
import com.ulises.test_core.TestDispatchers
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode

@ExtendWith(CoroutineTestRule::class)
@Execution(ExecutionMode.CONCURRENT)
class ScheduleDateDetailViewModelTest {

    @MockK
    private lateinit var addScheduledEventUseCase: AddScheduledEventUseCase
    private lateinit var scheduleDateDetailViewModel: ScheduleDateDetailViewModel
    private lateinit var userSessionManager: UserSessionManager

    @BeforeEach
    fun setUp() {
        userSessionManager = UserSessionManager()
        MockKAnnotations.init(this)
    }

    @Test
    fun demo() {
        assert(true)
    }
}