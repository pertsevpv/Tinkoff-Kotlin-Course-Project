package com.example.database.services

import com.example.database.dao.UserDao
import com.example.database.entities.User

class UserService {

    private val dao: UserDao = UserDao()

    fun findById(id: Int): User? =
        dao.findById(id)

    fun findAll(): List<User> =
        dao.findAll()

    fun findByName(name: String): User? =
        dao.findByName(name)

    fun contains(userName: String): Boolean =
        dao.contains(userName)

    fun save(file: User): User =
        dao.save(file)

    fun check(userName: String, hash: String): Boolean =
        dao.check(userName, hash)

    fun checkToken(token: String): Boolean =
        dao.checkToken(token)

}