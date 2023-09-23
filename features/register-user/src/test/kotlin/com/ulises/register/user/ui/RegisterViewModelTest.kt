package com.ulises.register.user.ui

import app.cash.turbine.test
import com.ulises.components.TextType
import com.ulises.register.user.models.UiState
import com.ulises.test_core.CoroutineTestRule
import com.ulises.test_core.TestDispatchers
import com.ulises.usecases.session.RegisterUserUseCase
import com.ulises.usecases.users.CreateUserUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import models.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode

@ExtendWith(CoroutineTestRule::class)
@Execution(ExecutionMode.CONCURRENT)
class RegisterViewModelTest {

    @MockK
    private lateinit var registerUserUseCase: RegisterUserUseCase

    @MockK
    private lateinit var createUserUseCase: CreateUserUseCase
    private lateinit var viewModel: RegisterViewModel

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = RegisterViewModel(
            dispatchers = TestDispatchers(),
            registerUserUseCase = registerUserUseCase,
            createUserUseCase = createUserUseCase
        )
    }

    @Test
    fun `All data form is filled and updated state accordingly and after all fields are filled button is enabled`() =
        runTest {
            viewModel.uiState.test {
                assertEquals(UiState(), awaitItem())
                viewModel.onTextChange(TextType.Email, email)
                assertEquals(UiState(email = email), awaitItem())
                viewModel.onTextChange(TextType.Password, password)
                assertEquals(UiState(email = email, password = password), awaitItem())
                viewModel.onTextChange(TextType.RePassword, password)
                assertEquals(
                    UiState(
                        email = email,
                        password = password,
                        rePassword = password
                    ),
                    awaitItem()
                )
                viewModel.onTextChange(TextType.Name, name)
                assertEquals(
                    UiState(
                        email = email,
                        password = password,
                        rePassword = password,
                        nameNickname = name
                    ), awaitItem()
                )
                assertEquals(
                    UiState(
                        email = email,
                        password = password,
                        rePassword = password,
                        nameNickname = name,
                        isEnabled = true
                    ), awaitItem()
                )
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `All fields filled and button enabled, but removal of name makes it disable again`() = runTest {
        viewModel.onTextChange(TextType.Email, email)
        viewModel.onTextChange(TextType.Password, password)
        viewModel.onTextChange(TextType.RePassword, password)
        viewModel.onTextChange(TextType.Name, name)
        viewModel.uiState.test {
            assertEquals(
                UiState(
                email = email,
                password = password,
                rePassword = password,
                nameNickname = name,
                isEnabled = true
                ), awaitItem()
            )
            viewModel.onTextChange(TextType.Name, "")
            skipItems(1)
            assertEquals(
                UiState(
                    email = email,
                    password = password,
                    rePassword = password,
                    nameNickname = "",
                    isEnabled = false
                ), awaitItem()
            )
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `Attempt to register a new user with success response`() = runTest {
        coEvery { registerUserUseCase(any(), any()) } returns user
        coEvery { createUserUseCase(any()) } just runs
        viewModel.onTextChange(TextType.Email, email)
        viewModel.onTextChange(TextType.Password, password)
        viewModel.onTextChange(TextType.RePassword, password)
        viewModel.onTextChange(TextType.Name, name)
        viewModel.uiState.test {
            skipItems(1)
            viewModel.onSignInButtonClick()
            assertTrue(awaitItem().navigateToHome)
            ensureAllEventsConsumed()
        }
        coVerify {
            registerUserUseCase(email, password)
            createUserUseCase(user)
        }
    }

    @Test
    fun `Attempt to register a new user with error while trying to register`() = runTest {
        val error = "Error registering user"
        coEvery { registerUserUseCase(any(), any()) } throws Exception(error)
        viewModel.onTextChange(TextType.Email, email)
        viewModel.onTextChange(TextType.Password, password)
        viewModel.onTextChange(TextType.RePassword, password)
        viewModel.onTextChange(TextType.Name, name)
        viewModel.uiState.test {
            skipItems(1)
            viewModel.onSignInButtonClick()
            val item = awaitItem()
            assertFalse(item.navigateToHome)
            assertEquals(error ,item.error)
            ensureAllEventsConsumed()
        }
        coVerify {
            registerUserUseCase(email, password)
        }
        coVerify(inverse = true) {
            createUserUseCase(any())
        }
    }

    @Test
    fun `Attempt to register a new user with error because User is null`() = runTest {
        val error = "Required value was null."
        coEvery { registerUserUseCase(any(), any()) } returns null
        viewModel.onTextChange(TextType.Email, email)
        viewModel.onTextChange(TextType.Password, password)
        viewModel.onTextChange(TextType.RePassword, password)
        viewModel.onTextChange(TextType.Name, name)
        viewModel.uiState.test {
            skipItems(1)
            viewModel.onSignInButtonClick()
            val item = awaitItem()
            assertFalse(item.navigateToHome)
            assertEquals(error ,item.error)
            ensureAllEventsConsumed()
        }
        coVerify {
            registerUserUseCase(email, password)
        }
        coVerify(inverse = true) {
            createUserUseCase(any())
        }
    }

    @Test
    fun `Attempt to register a new user but creation of user thrown exception`() = runTest {
        val error = "Error creating user"
        coEvery { registerUserUseCase(any(), any()) } returns user
        coEvery { createUserUseCase(any()) } throws Exception(error)
        viewModel.onTextChange(TextType.Email, email)
        viewModel.onTextChange(TextType.Password, password)
        viewModel.onTextChange(TextType.RePassword, password)
        viewModel.onTextChange(TextType.Name, name)
        viewModel.uiState.test {
            skipItems(1)
            viewModel.onSignInButtonClick()
            assertEquals(error, awaitItem().error)
            ensureAllEventsConsumed()
        }
        coVerify {
            registerUserUseCase(email, password)
            createUserUseCase(user)
        }
    }

    private companion object {
        const val email = "demo@email.com"
        const val password = "password"
        const val name = "My Name"
        val user = User("123", email, name)
    }
}