package com.example.randomuser.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.randomuser.data.source.local.dao.UserDao
import com.example.randomuser.data.source.local.dao.UserRemoteKeyDao
import com.example.randomuser.data.source.local.entity.UserEntity
import com.example.randomuser.data.source.local.entity.UserRemoteKeyEntity

@Database(entities = [UserEntity::class, UserRemoteKeyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userRemoteKeyDao(): UserRemoteKeyDao
}
