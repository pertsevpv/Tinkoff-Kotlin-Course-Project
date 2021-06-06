package com.example.controllers.queries

data class UserResponse(
    val message: String,
    val status: Boolean,
    val userId: Int? = null,
    val hash: String? = null
)