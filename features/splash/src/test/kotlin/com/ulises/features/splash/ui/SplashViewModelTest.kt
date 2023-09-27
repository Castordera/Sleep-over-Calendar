package com.ulises.features.splash.ui

import app.cash.turbine.test
import com.ulises.features.splash.models.UiState
import com.ulises.test_core.CoroutineTestRule
import com.ulises.usecase.session.GetCurrentUserUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode

@ExtendWith(CoroutineTestRule::class)
@Execution(ExecutionMode.CONCURRENT)
class SplashViewModelTest {

    @MockK
    private lateinit var getCurrentUserUseCase: com.ulises.usecase.session.GetCurrentUserUseCase
    private lateinit var viewModel: SplashViewModel

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Init configuration and user is returned from use case`() = runTest {
        coEvery { getCurrentUserUseCase() } returns mockk()
        initViewModel()
        viewModel.uiState.test {
            assertEquals(UiState(userFound = true), awaitItem())
            ensureAllEventsConsumed()
        }
        coVerify(exactly = 1) {
            getCurrentUserUseCase()
        }
    }

    @Test
    fun `Init configuration and get an exception from use case, uiState gets error message`() = runTest {
        val exceptionMessage = "Error here"
        coEvery { getCurrentUserUseCase() } throws Exception(exceptionMessage)
        initViewModel()
        viewModel.uiState.test {
            assertEquals(UiState(error = exceptionMessage), awaitItem())
            ensureAllEventsConsumed()
        }
        coVerify(exactly = 1) {
            getCurrentUserUseCase()
        }
    }

    /**
     * Because of ViewModel gets called at init
     */
    private fun initViewModel() {
        viewModel = SplashViewModel(
            dispatchers = com.ulises.test_core.TestDispatchers(),
            getCurrentUserUseCase = getCurrentUserUseCase
        )
    }
}