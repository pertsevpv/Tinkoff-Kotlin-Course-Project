package com.example.services

import com.example.Configs.server
import com.example.services.queries.*
import com.example.session.CurrentSession
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.json.JSONObject
import java.io.File

class AWSService {

    private val token: String = CurrentSession.currentUser?.hash ?: ""

    fun uploadFile(req: UploadFileRequest): BaseResponse {
        CurrentSession.checkAuthorization()
        val request = RestAssured.given()
        val requestBody = JSONObject()
            .put("byteArray", req.byteArray)
            .put("folder", req.folder)
            .put("name", req.name)
            .put("status", req.status)
        request.contentType(ContentType.JSON)
        request.body(requestBody.toString())
        val response = request.post("$server/aws/${token}/upload/")
        //println(response.asString())
        return ObjectMapper().readValue(response.body.asString(), BaseResponse::class.java)
    }

    fun deleteFile(req: DeleteFileRequest): BaseResponse {
        CurrentSession.checkAuthorization()
        val request = RestAssured.given()
        val requestBody = JSONObject()
            .put("fileName", req.fileName)
            .put("fileFolder", req.folderName)
        request.contentType(ContentType.JSON)
        request.body(requestBody.toString())
        val response = request.delete("$server/aws/${token}/delete/")
        return ObjectMapper().readValue(response.body.asString(), BaseResponse::class.java)
    }

    fun downloadFile(req: GetFileRequest): Boolean {
        CurrentSession.checkAuthorization()
        val request = RestAssured.given()
        val requestBody = JSONObject()
            .put("fileName", req.fileName)
            .put("fileFolder", req.fileFolder)
            .put("toPath", req.toPath)
        request.contentType(ContentType.JSON)
        request.body(requestBody.toString())
        val response = request.get("$server/aws/${token}/download/")
        //println(response.asString())
        val res = ObjectMapper().readValue(response.asString(), GetFileResponse::class.java)
        if (res.success.not()) return false
        writeFile(res.byteArray ?: byteArrayOf(), req.toPath)
        return true
    }

    private fun writeFile(byteArray: ByteArray, fileName: String): Boolean {
        val file = File(fileName)
        if (file.parent == null)
            return writeFile(byteArray, "downloaded-files/${fileName}")

        file.outputStream().use { stream ->
            return runCatching {
                file.mkdirs()
                stream.write(byteArray)
            }.isSuccess
        }
    }
}