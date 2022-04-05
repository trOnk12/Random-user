package com.example.randomuser.ui.feature.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.usecase.GetPagingUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UsersViewModel
@Inject constructor(
    getPagingUsersUseCase: GetPagingUsersUseCase
) : ViewModel() {

    val pagedUserList: Flow<PagingData<User>> =
        getPagingUsersUseCase(PagingConfig(pageSize = 12)).cachedIn(viewModelScope)

}

// We want to cache the currently displayed page result, we could include this logic into the domain
// layer and store the pages locally, but it would mean that if user fetches lots of pages on the mobile
// there would be lots of database entries which would result to huge size of the app
// we could also pass a User to the next screen and mark the User model as Serializable/Parcelable
// but that would be just ugly and not use the route system which is recommended to follow URL definition
// where id of the user fits nicely, also it would mean that the "parent" screen from which we would access
// the User model to pass to the next screen, would always need a reference to the User, but there may be
// cases where we do not have reference to that object
// also making the other screen work with id, prepares us for easier migration once the back-end
// has a end-point returning a user by id because,
// ideally, there should be an end-point on the back-end allowing us to get the user by id
// that way we would create an use case that would ask the back-end for that user
//@Singleton
//class GetUsersLocalCacheProvider @Inject constructor(
//    private val getPagingUsersUseCase: GetPagingUsersUseCase
//) {
//
//    private var currentUsersByUuid = mapOf<String, User>()
//
//    suspend fun getUsers(resultSize: Int = 15, page: Int = 1): Result<List<User>> {
//        val result = getPagingUsersUseCase(resultSize, page)
//
//        withContext(Dispatchers.Default) {
//            result.onSuccess { fetchedUsers ->
//                currentUsersByUuid = fetchedUsers.associateBy { it.uuid }
//            }
//        }
//
//        return result
//    }
//
//    fun getUser(uuid: String): User = currentUsersByUuid[uuid]!!
//
//}

data class UsersState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)