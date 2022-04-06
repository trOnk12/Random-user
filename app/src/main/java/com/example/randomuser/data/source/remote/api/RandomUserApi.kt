package com.example.randomuser.data.source.remote.api

import com.example.randomuser.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {

    @GET("api/")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("results") resultSize: Int,
        @Query("seed") seed: String
    ): UserResponse

}