package com.example.randomuser.domain.repository

import androidx.paging.PagingSource
import com.example.randomuser.data.source.local.UserLocalSource
import com.example.randomuser.data.source.local.entity.UserEntity
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userLocalSource: UserLocalSource
) {

    fun userPagingSource(): PagingSource<Int, UserEntity> = userLocalSource.getAllUsers()

    suspend fun getUser(id: String) = userLocalSource.getUser(id)

}