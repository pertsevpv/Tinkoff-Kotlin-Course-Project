package com.example.services

import com.example.services.queries.FileResponse

interface BaseFileService {

    fun contains(fileName: String, folderName: String): Boolean

    fun checkAccess(userId: Int, fileName: String, folderName: String): Boolean

    fun giveAccess(ownerId: Int, userName: String, folderName: String, fileName: String): FileResponse

    fun getAllAbleFiles(userId: Int): List<String>

}