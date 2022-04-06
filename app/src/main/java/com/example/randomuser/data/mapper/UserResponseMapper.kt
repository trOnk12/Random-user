package com.example.randomuser.data.mapper

import com.example.randomuser.data.model.UserResponse
import com.example.randomuser.data.source.local.entity.UserEntity
import com.example.randomuser.domain.model.User
import javax.inject.Inject

class UserResponseMapper @Inject constructor() {

    fun toUsers(userResponse: UserResponse): List<User> {
        return userResponse.results.map { result ->
            with(result) {
                User(
                    uuid = login.uuid,
                    name = name.first,
                    avatarUrl = picture.large,
                    email = email,
                    street = location.street.name,
                    city = location.city,
                    postcode = location.postcode,
                    gender = gender,
                    phone = phone,
                )
            }
        }
    }

    fun toEntities(userResponse: UserResponse): List<UserEntity> {
        return userResponse.results.map { result ->
            with(result) {
                UserEntity(
                    id = login.uuid,
                    name = name.first,
                    avatarUrl = picture.large,
                    email = email,
                    street = location.street.name,
                    city = location.city,
                    postcode = location.postcode,
                    gender = gender,
                    phone = phone,
                )
            }
        }
    }

}