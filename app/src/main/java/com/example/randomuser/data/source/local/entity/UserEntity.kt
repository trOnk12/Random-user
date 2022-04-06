package com.example.randomuser.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val avatarUrl: String,
    val email: String,
    val street: String,
    val city: String,
    val postcode: String,
    val gender: String,
    val phone: String,
)
