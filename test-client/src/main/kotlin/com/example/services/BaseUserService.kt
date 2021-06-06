package com.example.services

import com.example.services.queries.UserResponse

interface BaseUserService {

    fun authorize(userName: String, password: String): UserResponse

    fun register(userName: String, password: String): UserResponse

    fun getAllUsers(): List<String>
}