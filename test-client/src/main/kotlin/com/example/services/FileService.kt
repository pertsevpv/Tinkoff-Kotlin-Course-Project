package com.example.services

import com.example.Configs.server
import com.example.services.queries.FileResponse
import com.example.session.CurrentSession
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured


class FileService : BaseFileService {

    private val token: String = CurrentSession.currentUser?.hash ?: ""

    override fun contains(fileName: String, folderName: String): Boolean {
        CurrentSession.checkAuthorization()
        val response = RestAssured.get("$server/files/${token}/check/$folderName/$fileName")
        return ObjectMapper().readValue(response.body.asString(), Boolean::class.java)
    }

    override fun checkAccess(userId: Int, fileName: String, folderName: String): Boolean {
        CurrentSession.checkAuthorization()
        val response = RestAssured.get("$server/files/${token}/check/$userId/$folderName/$fileName")
        return ObjectMapper().readValue(response.body.asString(), Boolean::class.java)
    }

    override fun giveAccess(ownerId: Int, userName: String, folderName: String, fileName: String): FileResponse {
        CurrentSession.checkAuthorization()
        val response = RestAssured.post("$server/files/${token}/give/$ownerId/$userName/$folderName/$fileName")
        return ObjectMapper().readValue(response.body.asString(), FileResponse::class.java)
    }

    override fun getAllAbleFiles(userId: Int): List<String> {
        CurrentSession.checkAuthorization()
        val response = RestAssured.get("$server/files/${token}/$userId")
        return ObjectMapper().readValue(response.body.asString(), List::class.java).map { it.toString() }
    }

    fun getMyFiles(userId: Int): List<String> {
        CurrentSession.checkAuthorization()
        val response = RestAssured.get("$server/files/${token}/myfiles/$userId")
        return ObjectMapper().readValue(response.body.asString(), List::class.java).map { it.toString() }
    }

}