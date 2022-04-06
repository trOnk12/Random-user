package com.example.randomuser.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userRemoteKey")
data class UserRemoteKeyEntity(
    @PrimaryKey
    val userId: String,
    val previousPage: Int?,
    val nextPage: Int?
)