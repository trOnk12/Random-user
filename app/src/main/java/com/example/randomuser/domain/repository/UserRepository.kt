package com.example.randomuser.domain.repository

import androidx.paging.PagingSource
import com.example.randomuser.data.source.paging.UsersPagingSource
import com.example.randomuser.domain.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val usersPagingSource: UsersPagingSource
) {

    fun userPagingSource(): PagingSource<Int, User> = usersPagingSource.getPagingSource()

}