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
                id,
                name,
                avatarUrl
            )
        }
    }

    fun toUsers(userEntities: List<UserEntity>): List<User> {
        return userEntities.map { toUser(it) }
    }

}