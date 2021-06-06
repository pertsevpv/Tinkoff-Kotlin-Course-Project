package com.example.controllers

import com.example.database.services.UserService
import com.example.services.AWSS3Service
import com.example.services.queries.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/aws")
class AWSController(@Autowired @Qualifier("awsService") private val service: AWSS3Service) {


    @PostMapping("/{token}/upload")
    fun uploadFile(@RequestBody req: String, @PathVariable token: String): ResponseEntity<String> {
        return if (UserService().checkToken(token)) {
            val res = ObjectMapper().readValue(req, UploadFileRequest::class.java)
            ResponseEntity(service.uploadFile(res), HttpStatus.OK)
        } else
            ResponseEntity("Access Denied", HttpStatus.FORBIDDEN)
    }

    @DeleteMapping("/{token}/delete")
    fun deleteFile(@RequestBody req: String, @PathVariable token: String): ResponseEntity<String> {
        return if (UserService().checkToken(token)) {
            val res = ObjectMapper().readValue(req, DeleteFileRequest::class.java)
            ResponseEntity(service.deleteFile(res), HttpStatus.OK)
        } else
            ResponseEntity("Access Denied", HttpStatus.FORBIDDEN)
    }

    @GetMapping("/{token}/download")
    fun downloadFile(@RequestBody req: String, @PathVariable token: String): ResponseEntity<String> {
        return if (UserService().checkToken(token)) {
            val res = ObjectMapper().readValue(req, GetFileRequest::class.java)
            ResponseEntity(service.downloadFile(res).apply { println(this) }, HttpStatus.OK)
        } else
            ResponseEntity("Access Denied", HttpStatus.FORBIDDEN)
    }
}