package com.example.randomuser.domain

import com.example.randomuser.data.source.remote.UserRemoteSource
import com.example.randomuser.domain.mapper.UserResponseMapper
import com.example.randomuser.domain.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteSource: UserRemoteSource,
    private val userResponseMapper: UserResponseMapper
) {

    suspend fun getUsers(resultSize: Int): List<User> =
        userResponseMapper.toUsers(userResponse = userRemoteSource.getUsers(resultSize))

}