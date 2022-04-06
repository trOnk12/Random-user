package com.example.randomuser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.randomuser.domain.usecase.GetUserUseCase
import com.example.randomuser.utility.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @MockK
    lateinit var getUserUseCase: GetUserUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `'when getting paging users succeeds viewmodel propagates the result as expected'`() =
        runTest {

        }

}