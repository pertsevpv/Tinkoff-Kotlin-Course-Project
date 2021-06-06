package com.example.database.dao

import com.example.database.HibernateSessionFactoryUtil.sessionFactory
import com.example.database.entities.User

class UserDao {

    fun findById(id: Int): User? =
        sessionFactory?.openSession()?.get(User::class.java, id)

    fun findByName(name: String): User? =
        findAll().find { it.name == name }

    @Suppress("UNCHECKED_CAST")
    fun findAll(): List<User> {
        return sessionFactory!!.openSession().createQuery("From User").list() as List<User>
    }

    //NOTE колхоз
    fun contains(userName: String): Boolean {
        return findAll().any { it.name.equals(userName) }
    }

    fun check(userName: String, hash: String): Boolean {
        return findAll().any { it.name.equals(userName) && it.hash.equals(hash) }
    }

    fun checkToken(token: String): Boolean {
        return findAll().any { it.hash.equals(token) }
    }

    fun save(user: User): User {
        val session = sessionFactory!!.openSession()
        val tr = session.beginTransaction()
        session.save(user)
        tr.commit()
        session.close()
        return user
    }

}