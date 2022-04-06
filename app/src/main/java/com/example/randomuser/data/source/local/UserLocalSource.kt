package com.example.randomuser.data.source.local

import androidx.paging.PagingSource
import com.example.randomuser.data.source.local.dao.UserDao
import com.example.randomuser.data.source.local.entity.UserEntity
import javax.inject.Inject

class UserLocalSource @Inject constructor(
    private val userDao: UserDao
) {

    fun getAllUsers(): PagingSource<Int, UserEntity> = userDao.getAllUsers()

}