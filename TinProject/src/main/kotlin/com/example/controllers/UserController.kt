package com.example.controllers

import com.example.controllers.queries.UserResponse
import com.example.database.entities.User
import com.example.database.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<String>> {
        return ResponseEntity(UserService().findAll().map { it.name ?: "" }, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): ResponseEntity<User> {
        return ResponseEntity(UserService().findById(id), HttpStatus.OK)
    }

    @GetMapping("/{nickname}/{password}")
    fun authorizeUser(
        @PathVariable nickname: String,
        @PathVariable password: String
    ): ResponseEntity<UserResponse> {
        if (UserService().check(nickname, password).not()) {
            return ResponseEntity(UserResponse("Incorrect login or password", false), HttpStatus.BAD_REQUEST)
        }
        val user = UserService().findByName(nickname)
        return ResponseEntity(UserResponse("OK. Welcome, $nickname", true, user?.id, user?.hash), HttpStatus.OK)
    }

    @PostMapping("/{nickname}/{password}")
    fun registerUser(
        @PathVariable nickname: String,
        @PathVariable password: String
    ): ResponseEntity<UserResponse> {
        if (UserService().contains(nickname)) {
            return ResponseEntity(UserResponse("This nickname is already taken", false), HttpStatus.BAD_REQUEST)
        }
        val user = UserService().save(User(nickname, password))
        return ResponseEntity(UserResponse("OK. Welcome, $nickname", true, user.id, user.hash), HttpStatus.OK)
    }

}