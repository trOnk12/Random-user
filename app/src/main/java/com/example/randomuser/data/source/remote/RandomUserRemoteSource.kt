package com.example.randomuser.data.source.remote

import UserResponse
import com.example.randomuser.data.RandomUserApi
import javax.inject.Inject

class RandomUserRemoteSource @Inject constructor(private val randomUserApi: RandomUserApi) {

    suspend fun getUsers(resultSize: Int): UserResponse = randomUserApi.getUsers(resultSize)

}