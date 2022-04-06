package com.example.randomuser.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.randomuser.data.source.local.entity.UserEntity


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Query("SELECT * FROM user")
    fun getAllUsers(): PagingSource<Int, UserEntity>

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUser(id: String): UserEntity

}
