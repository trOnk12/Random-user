package com.example.randomuser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.usecase.GetPagingUsersUseCase
import com.example.randomuser.ui.feature.users.UsersViewModel
import com.example.randomuser.ui.feature.users.model.UserItem
import com.example.randomuser.utility.MainCoroutineRule
import com.example.randomuser.utility.TestLazyPaging
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalPagingApi
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
    fun `'when getting paging users succeeds viewmodel propagates the result as expected'`() =
        runTest {
            //given
            coEvery { getPagingUsersUseCase.invoke(any()) } returns flow {
                emit(
                    PagingData.from(
                        listOf(
                            testUser
                        )
                    )
                )
            }
            //when
            val pagedUserListPaging = TestLazyPaging(
                testDispatcher = coroutineRule.testDispatcher,
                flow = UsersViewModel(getPagingUsersUseCase).pagedUserList
            )
            pagedUserListPaging.collectPagingData()
            // then
            assertEquals(
                listOf(UserItem(id = "testUuid", name = "testName", avatarUrl = "testAvatarUrl")),
                pagedUserListPaging.getSnapShot().items
            )
            coVerify { getPagingUsersUseCase.invoke(any()) }
        }

}


private val testUser = User(
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