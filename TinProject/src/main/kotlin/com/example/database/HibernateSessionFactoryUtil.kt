package com.example.database

import com.example.database.entities.File
import com.example.database.entities.FileStatus
import com.example.database.entities.User
import org.hibernate.boot.registry.StandardServiceRegistryBuilder

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import java.lang.Exception

object HibernateSessionFactoryUtil {
    var sessionFactory: SessionFactory? = null
        get() {
            if (field == null) {
                try {
                    val configuration: Configuration = Configuration().configure()
                    configuration.addAnnotatedClass(File::class.java)
                    configuration.addAnnotatedClass(User::class.java)
                    configuration.addAnnotatedClass(FileStatus::class.java)
                    val builder = StandardServiceRegistryBuilder().applySettings(configuration.properties)
                    field = configuration.buildSessionFactory(builder.build())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return field
        }
}