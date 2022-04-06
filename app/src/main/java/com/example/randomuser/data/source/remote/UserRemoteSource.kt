package com.example.randomuser.data.source.remote

import com.example.randomuser.data.source.remote.api.RandomUserApi
import com.example.randomuser.data.mapper.UserResponseMapper
import com.example.randomuser.domain.model.User
import javax.inject.Inject

class UserRemoteSource @Inject constructor(
    private val randomUserApi: RandomUserApi,
    private val userResponseMapper: UserResponseMapper
) {

    suspend fun getUsers(page: Int,resultSize:Int): List<User> =
        userResponseMapper.toUsers(userResponse = randomUserApi.getUsers(
            page = page,
            resultSize = resultSize,
            seed = "abc"
        ))

}

