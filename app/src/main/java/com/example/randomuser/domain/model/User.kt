package com.example.randomuser.domain.model

data class User(
    val uuid: String,
    val avatarUrl: String,
    val name: String,
    val email: String,
    val street: String,
    val city: String,
    val postcode: String,
    val gender: String,
    val phone: String,
)