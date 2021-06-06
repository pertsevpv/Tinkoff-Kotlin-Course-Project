package com.example.database.services

import com.example.database.dao.FileStatusDao
import com.example.database.entities.File
import com.example.database.entities.FileStatus

class FileStatusService {

    private val dao: FileStatusDao = FileStatusDao()

    fun findById(id: Int): FileStatus? =
        dao.findById(id)

    fun findAll(): List<FileStatus> =
        dao.findAll()

    fun findAllAbleFiles(userId: Int): List<File> =
        dao.findAllAbleFiles(userId)

    fun findByOwnerId(userId: Int): List<FileStatus> =
        dao.findByOwnerId(userId)

    fun getRec(userId: Int, ownerId: Int, fileId: Int): FileStatus? =
        dao.getRec(userId, ownerId, fileId)

    fun getOwner(fileId: Int): FileStatus? =
        dao.getOwner(fileId)

    fun checkAccess(fileId: Int, userId: Int): Boolean =
        dao.checkAccess(fileId, userId)

    fun contains(userId: Int, fileId: Int): Boolean =
        dao.contains(userId, fileId)

    fun save(file: FileStatus) =
        dao.save(file)

    fun delete(obj: FileStatus): FileStatus =
        dao.delete(obj)

    fun deleteByFileId(fileId: Int): List<FileStatus> =
        dao.deleteByFileId(fileId)

}