package com.example.randomuser.data.source.local

import com.example.randomuser.data.mapper.UserEntityMapper
import com.example.randomuser.data.source.local.dao.UserDao
import javax.inject.Inject

class UserLocalSource @Inject constructor(
    private val userEntityMapper: UserEntityMapper,
    private val userDao: UserDao
) {
    suspend fun getUser(id: String) = userEntityMapper.toUser(userDao.getUser(id))
}