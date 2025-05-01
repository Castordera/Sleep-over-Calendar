package com.ulises.user.detail.ui

import app.cash.turbine.test
import com.ulises.session.UserSessionManager
import com.ulises.test_core.CoroutineTestRule
import com.ulises.test_core.TestDispatchers
import com.ulises.usecase.session.CloseSessionUseCase
import com.ulises.user.detail.models.Intents
import com.ulises.user.detail.models.UiState
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
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
class UserViewModelTest {

    @MockK
    private lateinit var closeSessionUseCase: CloseSessionUseCase
    private lateinit var userSessionManager: UserSessionManager
    private lateinit var viewModel: UserViewModel

    @BeforeEach
    fun setup() {
        userSessionManager = spyk(UserSessionManager())
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `Get User Data and update UiState`() = runTest {
        val userMock = mockk<User>()
        userSessionManager.updateUserSessionState(userMock)
        initViewModel()
        //
        viewModel.uiState.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(isLoading = true), awaitItem())
            assertEquals(UiState(user = userMock), awaitItem())
            ensureAllEventsConsumed()
        }
        //
        verify(exactly = 1) {
            userSessionManager.userSessionState
        }
    }

    @Test
    fun `Attempt to logout with success`() = runTest {
        val userMock = mockk<User>()
        userSessionManager.updateUserSessionState(userMock)
        initViewModel()
        //
        viewModel.uiState.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(isLoading = true), awaitItem())
            assertEquals(UiState(user = userMock), awaitItem())
            viewModel.onHandleIntent(Intents.LogoutClicked)
            assertEquals(UiState(loggedOutAction = true), awaitItem())
            ensureAllEventsConsumed()
        }
        //
        verify(atLeast = 1) {
            userSessionManager.updateUserSessionState(any())
            userSessionManager.userSessionState
            userSessionManager.updateUserSessionState(null)
        }
        coVerify(exactly = 1) {
            closeSessionUseCase()
        }
    }

    private fun initViewModel() {
        viewModel = UserViewModel(
            dispatchers = TestDispatchers(),
            closeSessionUseCase = closeSessionUseCase,
            userSessionManager = userSessionManager,
        )
    }
}