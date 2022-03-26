package com.example.randomuser.domain.mapper

import com.example.randomuser.data.model.UserResponse
import com.example.randomuser.domain.model.User

class UserResponseMapper {

    fun toUsers(userResponse: UserResponse): List<User> {
        return userResponse.results.map { result ->
            User(result.id.value)
        }
    }

}