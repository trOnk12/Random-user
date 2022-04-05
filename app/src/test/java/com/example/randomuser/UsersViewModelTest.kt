package com.example.randomuser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.usecase.GetPagingUsersUseCase
import com.example.randomuser.ui.feature.users.UsersState
import com.example.randomuser.ui.feature.users.UsersViewModel
import com.example.randomuser.utility.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UsersViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @MockK
    lateinit var getPagingUsersUseCase: GetPagingUsersUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `'when getting users succeeds viewmodel handles the success as expected'`() = runTest {
        //given
        coEvery { getPagingUsersUseCase(any()) } returns Result.success(listOf(User("testId")))
        //when
        val usersViewModel = UsersViewModel(getPagingUsersUseCase)
        // then
        usersViewModel.userState.test {
            assertEquals(
                UsersState(
                    isLoading = false,
                    users = testUsers,
                    errorMessage = ""
                ),
                awaitItem()
            )
        }
        coVerify { getPagingUsersUseCase(any()) }
    }

    @Test
    fun `'when getting users fails viewmodel handles the failure as expected'`() = runTest {
        //given
        coEvery { getPagingUsersUseCase(any()) } returns Result.failure(IllegalStateException())
        //when
        val usersViewModel = UsersViewModel(getPagingUsersUseCase)
        // then
        usersViewModel.userState.test {
            assertEquals(
                UsersState(
                    isLoading = false,
                    errorMessage = "we couldn't get the users",
                    users = emptyList()
                ),
                awaitItem()
            )
        }
        coVerify { getPagingUsersUseCase(any()) }
    }

    private val testUsers = listOf(User("testId"))

}