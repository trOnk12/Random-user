package com.example.randomuser.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.randomuser.data.source.local.entity.UserRemoteKeyEntity

@Dao
interface UserRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userRemoteKeyEntity: UserRemoteKeyEntity)

    @Query("SELECT * FROM userRemoteKey WHERE userId = :userId")
    suspend fun getRemoteKey(userId: String): UserRemoteKeyEntity

}