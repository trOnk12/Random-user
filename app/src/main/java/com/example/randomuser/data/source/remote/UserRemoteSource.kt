package com.example.randomuser.data.source.remote

import com.example.randomuser.data.RandomUserApi
import com.example.randomuser.data.model.UserResponse
import javax.inject.Inject

class UserRemoteSource @Inject constructor(
    private val randomUserApi: RandomUserApi
) {

    suspend fun getUsers(resultSize: Int): UserResponse = randomUserApi.getUsers(resultSize)

}

