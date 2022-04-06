package com.example.randomuser.data.source.local

import com.example.randomuser.data.source.paging.CurrentPageUserSource
import com.example.randomuser.domain.model.User
import javax.inject.Inject

class UserLocalSource @Inject constructor(
    private val currentPageUserSource: CurrentPageUserSource
) {

    fun getUserById(id: String) = currentPageUserSource.getUserByIdFromCurrentPage(id)

    suspend fun addUsers(users: List<User>) {
        currentPageUserSource.addUsers(users)
    }
}