package com.example.randomuser.data

import com.example.randomuser.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RandomUserApi {

    @GET("api/")
    suspend fun getUsers(@Query("results") resultSize: Int): UserResponse

}