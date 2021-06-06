package com.example.database.dao

import com.example.database.entities.File
import com.example.database.HibernateSessionFactoryUtil.sessionFactory

//Spring data
class FileDao {

    fun findById(id: Int): File? =
        sessionFactory?.openSession()?.get(File::class.java, id)

    @Suppress("UNCHECKED_CAST")
    fun findAll(): List<File> {
        return sessionFactory!!.openSession().createQuery("From File").list() as List<File>
    }

    fun contains(fileName: String, folderName: String): Boolean {
        return findAll().any { (it.fileName == fileName) && (it.folderName == folderName) }
    }

    fun findByFileAndFolder(fileName: String, folderName: String): File? {
        return findAll().find { (it.fileName == fileName) && (it.folderName == folderName) }
    }

    fun findByFolder(folderName: String): List<File> {
        return findAll().filter { it.folderName == folderName }
    }

    fun save(file: File): File {
        val session = sessionFactory!!.openSession()
        val tr = session.beginTransaction()
        session.save(file)
        tr.commit()
        session.close()
        return file
    }

    fun delete(file: File): File {
        val session = sessionFactory!!.openSession()
        val tx1 = session.beginTransaction()
        session.delete(file)
        tx1.commit()
        session.close()
        return file
    }

}