package com.example.randomuser.domain.mapper

import com.example.randomuser.data.model.UserResponse
import com.example.randomuser.domain.model.User
import javax.inject.Inject

class UserResponseMapper @Inject constructor() {

    fun toUsers(userResponse: UserResponse): List<User> {
        return userResponse.results.map { result ->
            User(
                uuid = result.login.uuid,
                name = result.name.first
            )
        }
    }

}