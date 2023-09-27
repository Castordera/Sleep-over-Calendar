package com.ulises.user.detail.ui

import app.cash.turbine.test
import com.ulises.test_core.CoroutineTestRule
import com.ulises.test_core.TestDispatchers
import com.ulises.usecase.session.CloseSessionUseCase
import com.ulises.user.detail.models.UiState
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
class UserViewModelTest {

    @MockK
    private lateinit var closeSessionUseCase: com.ulises.usecase.session.CloseSessionUseCase
    private lateinit var viewModel: UserViewModel

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = UserViewModel(
            dispatchers = TestDispatchers(),
            closeSessionUseCase = closeSessionUseCase
        )
    }

    @Test
    fun `Attempt to logout with success`() = runTest {
        coEvery { closeSessionUseCase() } just runs
        viewModel.uiState.test {
            assertEquals(UiState(), awaitItem())
            viewModel.onLogoutClicked()
            assertEquals(UiState(loggedOutAction = true), awaitItem())
        }
        coVerify { closeSessionUseCase() }
    }
}