package com.example.randomuser.di

import com.example.randomuser.data.RandomUserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideRandomUserApi(): RandomUserApi {
        return Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .build()
            .create(RandomUserApi::class.java)
    }

}