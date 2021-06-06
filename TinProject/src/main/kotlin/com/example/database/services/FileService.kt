package com.example.database.services

import com.example.database.HibernateSessionFactoryUtil
import com.example.database.dao.FileDao
import com.example.database.entities.File

class FileService {

    private val dao: FileDao = FileDao()

    fun findById(id: Int): File? =
        dao.findById(id)

    fun findAll(): List<File> =
        dao.findAll()

    fun findByFileAndFolder(fileName: String, folderName: String): File? =
        dao.findByFileAndFolder(fileName, folderName)

    fun findByFolder(folderName: String): List<File> =
        dao.findByFolder(folderName)

    fun contains(fileName: String, folderName: String): Boolean =
        dao.contains(fileName, folderName)

    fun save(file: File): File =
        dao.save(file)

    fun delete(file: File): File =
        dao.delete(file)

}