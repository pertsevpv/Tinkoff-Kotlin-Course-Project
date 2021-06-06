package com.example.session

import com.example.exceptions.NoAuthorizationException
import com.example.services.UserService

object CurrentSession {

    var currentUser: User? = null

    fun authorize(userName: String, password: String): String {
        val userN = userName.toLowerCase()
        if (currentUser != null)
            return "You are authorized already. Please, exit to change account"
        val resp = UserService().authorize(userN, password)
        if (resp.status)
            currentUser = User(resp.userId, userN, resp.hash)
        return resp.message
    }

    fun register(userName: String, password: String): String {
        val userN = userName.toLowerCase()
        if (currentUser != null)
            return "You are authorized already. Please, exit to change account"
        val resp = UserService().register(userN, password)
        if (resp.status)
            currentUser = User(resp.userId, userN, resp.hash)
        return resp.message
    }

    fun exited(): String {
        return if (currentUser != null) {
            val mes = "Goodbye, ${currentUser!!.name}"
            currentUser = null
            mes
        } else
            "You are already exited"
    }

    fun checkAuthorization(){
        if (currentUser == null)
            throw NoAuthorizationException()
    }

}