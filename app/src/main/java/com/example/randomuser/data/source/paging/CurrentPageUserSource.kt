package com.example.randomuser.data.source.paging

import com.example.randomuser.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


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
@Singleton
class CurrentPageUserSource @Inject constructor() {

    private var currentUsersByUuid = mapOf<String, User>()

    fun getUserByIdFromCurrentPage(id: String): User? {
        return currentUsersByUuid[id]
    }

    suspend fun addUsers(users: List<User>) {
        withContext(Dispatchers.Default) {
            currentUsersByUuid = users.associateBy { it.uuid }
        }
    }

}