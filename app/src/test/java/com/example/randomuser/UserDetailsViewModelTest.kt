package com.example.randomuser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.usecase.GetUserUseCase
import com.example.randomuser.ui.feature.userdetail.UserDetailState
import com.example.randomuser.ui.feature.userdetail.UserDetailViewModel
import com.example.randomuser.ui.feature.userdetail.model.UserDetail
import com.example.randomuser.utility.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @MockK
    lateinit var getUserUseCase: GetUserUseCase

    @MockK(relaxed = true)
    lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `'when getting users details viewmodel propagates the result as expected'`() =
        runTest {
            //given
            coEvery { getUserUseCase.invoke(any()) } returns Result.success(
                User(
                    uuid = "testUuid",
                    avatarUrl = "testAvatarUrl",
                    name = "testName",
                    email = "testEmail",
                    street = "testStreet",
                    city = "testCity",
                    postcode = "testPostcode",
                    gender = "testGender",
                    phone = "testPhone"
                )
            )

            //when
            val viewModel =
                UserDetailViewModel(
                    savedStateHandle = savedStateHandle,
                    getUserUseCase = getUserUseCase
                )
            // then
            viewModel.userDetailState.test {
                assertEquals(
                    awaitItem(), UserDetailState(
                        userDetail = UserDetail(
                            avatarUrl = "testAvatarUrl",
                            name = "testName",
                            email = "testEmail",
                            street = "testStreet",
                            city = "testCity",
                            postcode = "testPostcode",
                            gender = "testGender",
                            phone = "testPhone"
                        ), isLoading = false,
                        errorMessage = ""
                    )
                )

                awaitComplete()
            }
        }

    @Test
    fun `'when getting users details fails viewmodel propagates the error as expected'`() =
        runTest {
            //given
            coEvery { getUserUseCase.invoke(any()) } returns Result.failure(Exception("error is thrown"))
            //when
            val viewModel =
                UserDetailViewModel(
                    savedStateHandle = savedStateHandle,
                    getUserUseCase = getUserUseCase
                )
            // then
            viewModel.userDetailState.test {
                assertEquals(
                    awaitItem(), UserDetailState(
                        userDetail = null, isLoading = false,
                        errorMessage = "We could not get the user"
                    )
                )

                awaitComplete()
            }
        }

}