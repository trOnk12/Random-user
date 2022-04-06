package com.example.randomuser.domain.repository

import androidx.paging.PagingSource
import com.example.randomuser.data.source.local.UserLocalSource
import com.example.randomuser.data.source.paging.UsersPagingSource
import com.example.randomuser.domain.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val usersPagingSource: UsersPagingSource,
    private val userLocalSource: UserLocalSource
) {

    fun userPagingSource(): PagingSource<Int, User> = usersPagingSource.getPagingSource()

    fun getUserById(id: String): User? = userLocalSource.getUserById(id)

}