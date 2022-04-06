package com.example.randomuser.domain.repository

import androidx.paging.PagingSource
import com.example.randomuser.data.source.local.dao.UserDao
import com.example.randomuser.data.source.local.entity.UserEntity
import javax.inject.Inject

class UserPagingRepository @Inject constructor(private val userDao: UserDao) {

    fun userPagingSource(): PagingSource<Int, UserEntity> = userDao.getAllUsers()

}