package com.example.database.dao

import com.example.database.HibernateSessionFactoryUtil.sessionFactory
import com.example.database.entities.File
import com.example.database.entities.FileStatus
import com.example.database.services.FileService

class FileStatusDao {

    fun findById(id: Int): FileStatus? =
        sessionFactory?.openSession()?.get(FileStatus::class.java, id)

    fun findByUserId(userId: Int): List<FileStatus> =
        findAll().filter { it.userId == userId }

    fun findByOwnerId(userId: Int): List<FileStatus> =
        findAll().filter { it.ownerId == userId }

    fun findByFileId(fileId: Int): List<FileStatus> =
        findAll().filter { it.fileId == fileId }

    fun findAllAbleFiles(userId: Int):List<File> =
        findAll().filter { it.userId == userId || it.status.equals("public") }
            .mapNotNull { FileService().findById(it.fileId!!) }.toSet().toList()

    @Suppress("UNCHECKED_CAST")
    fun findAll(): List<FileStatus> {
        return sessionFactory!!.openSession().createQuery("From FileStatus")
            .list() as List<FileStatus>
    }

    fun getRec(userId: Int, ownerId: Int, fileId: Int): FileStatus? {
        return findAll().find { it.userId == userId && it.fileId == fileId && it.fileId == fileId }
    }

    fun getOwner(fileId: Int): FileStatus? {
        return findAll().find { it.fileId == fileId && it.ownerId == it.userId }
    }

    fun contains(userId: Int, fileId: Int): Boolean {
        return findAll().any { it.userId == userId && it.fileId == fileId }
    }

    fun checkAccess(fileId: Int, userId: Int): Boolean {
        return findAll().any {
            (it.fileId == fileId && it.userId == userId) ||
                    (it.fileId == fileId && it.status.equals("public"))
        }
    }

    fun save(obj: FileStatus) {
        val session = sessionFactory!!.openSession()
        val tr = session.beginTransaction()
        session.save(obj)
        tr.commit()
        session.close()
    }

    fun delete(obj: FileStatus): FileStatus {
        val session = sessionFactory!!.openSession()
        val tx1 = session.beginTransaction()
        session.delete(obj)
        tx1.commit()
        session.close()
        return obj
    }

    fun deleteByFileId(fileId: Int): List<FileStatus> {
        val list = findAll().filter { it.fileId == fileId }

        val session = sessionFactory!!.openSession()
        val tx1 = session.beginTransaction()
        list.forEach { session.delete(it) }
        tx1.commit()

        session.close()
        return list
    }

}