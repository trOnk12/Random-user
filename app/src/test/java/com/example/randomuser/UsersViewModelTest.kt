package com.example.randomuser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.usecase.GetUsersUseCase
import com.example.randomuser.ui.feature.users.UsersViewModel
import com.example.randomuser.utility.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
    lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

    }

    @Test
    fun `'when getting results fails state handles the failure correctly'`() = runTest {
        //given
        coEvery { getUsersUseCase(any()) } returns Result.success(listOf(User("testId")))
        //when
        val usersViewModel = UsersViewModel(getUsersUseCase)
        //then
        usersViewModel.userState

    }


}