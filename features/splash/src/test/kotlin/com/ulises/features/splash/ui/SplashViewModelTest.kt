package com.ulises.features.splash.ui

import app.cash.turbine.test
import com.ulises.features.splash.models.UiState
import com.ulises.session.UserSessionManager
import com.ulises.test_core.CoroutineTestRule
import com.ulises.usecase.user.GetCurrentUserUseCase
import io.mockk.MockKAnnotations
import io.mockk.called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import models.User
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
    private lateinit var getCurrentUserUseCase: GetCurrentUserUseCase

    @MockK
    private lateinit var userSessionManager: UserSessionManager
    private lateinit var viewModel: SplashViewModel

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Init configuration and user is returned from use case and pass it to manager`() = runTest {
        val user = mockk<User>()
        coEvery { getCurrentUserUseCase() } returns user
        every { userSessionManager.updateUserSessionState(any()) } just runs
        initViewModel()
        viewModel.uiState.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(userFound = true, isLoading = false), awaitItem())
            ensureAllEventsConsumed()
        }
        coVerify(exactly = 1) {
            getCurrentUserUseCase()
            userSessionManager.updateUserSessionState(user)
        }
    }

    @Test
    fun `Init configuration and get an exception from use case, uiState gets error message`() =
        runTest {
            val exceptionMessage = "Error here"
            coEvery { getCurrentUserUseCase() } throws Exception(exceptionMessage)
            initViewModel()
            viewModel.uiState.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(error = exceptionMessage, isLoading = false), awaitItem())
                ensureAllEventsConsumed()
            }
            coVerify(exactly = 1) {
                getCurrentUserUseCase()
            }
            verify {
                userSessionManager wasNot called
            }
        }

    /**
     * Because of ViewModel gets called at init
     */
    private fun initViewModel() {
        viewModel = SplashViewModel(
            dispatchers = com.ulises.test_core.TestDispatchers(),
            getCurrentUserUseCase = getCurrentUserUseCase,
            userSessionManager = userSessionManager,
        )
    }
}