package com.example.randomuser.domain.repository

import com.example.randomuser.data.source.local.UserLocalSource
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userLocalSource: UserLocalSource
) {

    suspend fun getUser(id: String) = userLocalSource.getUser(id)

}