package com.example.services

import com.example.Configs.server
import com.example.services.queries.UserResponse
import com.example.tools.Hasher
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured

class UserService : BaseUserService {

    override fun authorize(userName: String, password: String): UserResponse {
        val hashedPassword = Hasher.sha512Hex(password)
        val response = RestAssured.get("$server/users/$userName/$hashedPassword")
        return ObjectMapper().readValue(response.body.asString(), UserResponse::class.java)
    }

    override fun register(userName: String, password: String): UserResponse {
        val hashedPassword = Hasher.sha512Hex(password)
        val response = RestAssured.post("$server/users/$userName/$hashedPassword")
        return ObjectMapper().readValue(response.body.asString(), UserResponse::class.java)
    }

    override fun getAllUsers(): List<String> {
        val response = RestAssured.get("$server/users")
        return ObjectMapper().readValue(response.body.asString(), List::class.java).map { it.toString() }
    }

}