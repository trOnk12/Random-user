package com.example.randomuser.data.mapper

import com.example.randomuser.data.source.local.entity.UserEntity
import com.example.randomuser.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserEntityMapper @Inject constructor() {

    fun toUser(userEntity: UserEntity): User {
        return with(userEntity) {
            User(
                uuid = id,
                name = name,
                avatarUrl = avatarUrl,
                email = email,
                street = street,
                city = city,
                postcode = postcode,
                gender = gender,
                phone = phone
            )
        }
    }

}