package com.example.randomuser.data

import UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RandomUserApi {

    @GET("api")
    suspend fun getUsers(@Path("results") resultSize: Int): UserResponse

}