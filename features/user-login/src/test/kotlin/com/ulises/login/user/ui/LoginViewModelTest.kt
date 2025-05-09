package com.ulises.login.user.ui

import app.cash.turbine.test
import com.ulises.login.user.model.Intents
import com.ulises.login.user.model.TextFieldType
import com.ulises.login.user.model.UiState
import com.ulises.navigation.Screens
import com.ulises.test_core.CoroutineTestRule
import com.ulises.test_core.TestDispatchers
import com.ulises.usecase.session.LoginUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode

@ExtendWith(CoroutineTestRule::class)
@Execution(ExecutionMode.CONCURRENT)
class LoginViewModelTest {

    @MockK
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var viewModel: LoginViewModel


    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = LoginViewModel(
            dispatchers = TestDispatchers(),
            loginUseCase = loginUseCase,
        )
    }

    @Test
    fun `Attempt to login but no data filled and error returned and cleared after`() = runTest {
        val error = "Error for testing"
        coEvery { loginUseCase(any(), any()) } throws Exception(error)
        viewModel.uiState.test {
            assertEquals(UiState(), awaitItem())
            viewModel.onHandleIntent(Intents.LoginClicked)
            assertEquals(error, awaitItem().error)
            viewModel.onErrorShowed()
            assertEquals(UiState(), awaitItem())
            ensureAllEventsConsumed()
        }
        coVerify {
            loginUseCase("", "")
        }
    }

    @Test
    fun `Should update UI to navigate to a different screen and clear after`() = runTest {
        viewModel.uiState.test {
            assertEquals(UiState(), awaitItem())
            viewModel.onHandleIntent(Intents.SignInClicked)
            assertEquals(UiState(navigateTo = Screens.SignIn), awaitItem())
            viewModel.onNavigatedToRegister()
            assertEquals(UiState(), awaitItem())
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `Should update email and password and react accordingly`() = runTest {
        viewModel.uiState.test {
            skipItems(1)
            viewModel.onHandleIntent(Intents.UpdateTextField(TextFieldType.Email, EMAIL))
            assertEquals(viewModel.getTextFieldValue(TextFieldType.Email), EMAIL)
            viewModel.onHandleIntent(Intents.UpdateTextField(TextFieldType.Password, PASSWORD))
            assertEquals(viewModel.getTextFieldValue(TextFieldType.Password), PASSWORD)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `Attempt to Login with success response`() = runTest {
        coEvery { loginUseCase(any(), any()) } returns mockk()
        viewModel.uiState.test {
            skipItems(1)
            viewModel.onHandleIntent(Intents.UpdateTextField(TextFieldType.Email, EMAIL))
            viewModel.onHandleIntent(Intents.UpdateTextField(TextFieldType.Password, PASSWORD))
            viewModel.onHandleIntent(Intents.LoginClicked)
            assertEquals(awaitItem().navigateTo, Screens.Home)
            ensureAllEventsConsumed()
        }
        coVerify {
            loginUseCase(EMAIL, PASSWORD)
        }
    }

    private companion object {
        const val EMAIL = "demo@email.com"
        const val PASSWORD = "demo_password"
    }
}