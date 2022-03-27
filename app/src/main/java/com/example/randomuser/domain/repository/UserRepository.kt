package com.example.randomuser.domain.repository

import com.example.randomuser.data.source.remote.UserRemoteSource
import com.example.randomuser.domain.mapper.UserResponseMapper
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteSource: UserRemoteSource,
    private val userResponseMapper: UserResponseMapper
) {

    suspend fun getUsers(resultSize: Int) =
        userResponseMapper.toUsers(userResponse = userRemoteSource.getUsers(resultSize))

}