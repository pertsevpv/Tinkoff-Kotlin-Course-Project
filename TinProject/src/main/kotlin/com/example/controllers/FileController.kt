package com.example.controllers

import com.example.controllers.queries.FileResponse
import com.example.database.entities.File
import com.example.database.entities.FileStatus
import com.example.database.services.FileService
import com.example.database.services.FileStatusService
import com.example.database.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/files")
class FileController {

    @GetMapping("/{token}/check/{folderName}/{fileName}")
    fun checkIfExists(
        @PathVariable folderName: String,
        @PathVariable fileName: String, @PathVariable token: String
    ): ResponseEntity<Boolean> {
        return if (UserService().checkToken(token)) {
            ResponseEntity(FileService().contains(fileName, folderName), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.FORBIDDEN)
        }
    }

    @GetMapping("/{token}/myfiles/{userId}")
    fun getMyFiles(@PathVariable userId: Int, @PathVariable token: String): ResponseEntity<List<String>> {
        return if (UserService().checkToken(token)) {
            val userName = UserService().findById(userId)!!.name!!
            ResponseEntity(FileService().findByFolder(userName).map { it.toString() }, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.FORBIDDEN)
        }
    }

    @GetMapping("/{token}/{userId}")
    fun getAllAbleFiles(@PathVariable userId: Int, @PathVariable token: String): ResponseEntity<List<String>> {
        return if (UserService().checkToken(token)) {
            ResponseEntity(FileStatusService().findAllAbleFiles(userId).map { it.toString() }, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.FORBIDDEN)
        }
    }

    @GetMapping("/{token}/check/{userId}/{folderName}/{fileName}")
    fun checkAccess(
        @PathVariable userId: Int,
        @PathVariable folderName: String,
        @PathVariable fileName: String, @PathVariable token: String
    ): ResponseEntity<Boolean> {
        return if (UserService().checkToken(token)) {
            val fileId = FileService().findByFileAndFolder(fileName, folderName)!!.id!!
            ResponseEntity(FileStatusService().checkAccess(fileId, userId), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.FORBIDDEN)
        }
    }

    @PostMapping("/{token}/give/{ownerId}/{userName}/{fileFolder}/{fileName}")
    fun giveAccess(
        @PathVariable ownerId: Int,
        @PathVariable userName: String,
        @PathVariable fileFolder: String,
        @PathVariable fileName: String, @PathVariable token: String
    ): ResponseEntity<FileResponse> {
        if (UserService().checkToken(token)) {
            val file = FileService().findByFileAndFolder(fileName, fileFolder)
                ?: return ResponseEntity(FileResponse("You aren't owner of this file", false), HttpStatus.BAD_REQUEST)
            val rec = FileStatusService().getOwner(file.id!!)!!
            if (rec.ownerId != ownerId)
                return ResponseEntity(FileResponse("You aren't owner of this file", false), HttpStatus.BAD_REQUEST)
            val toUser = UserService().findByName(userName)!!
            FileStatusService().save(FileStatus(toUser.id!!, file.id!!, ownerId, "private"))
            return ResponseEntity(
                FileResponse("Now $userName have access to file $fileName from $fileFolder", true),
                HttpStatus.OK
            )
        } else {
            return ResponseEntity(HttpStatus.FORBIDDEN)
        }
    }
}