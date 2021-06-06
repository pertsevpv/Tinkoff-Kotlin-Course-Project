package com.example.services.queries

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.io.File
import java.lang.Exception

@JsonAutoDetect
class UploadFileRequest(
    @JsonProperty("byteArray") var byteArray: ByteArray,
    @JsonProperty("folder") var folder: String,
    @JsonProperty("name") var name: String,
    @JsonProperty("status") var status: String
)

data class UploadFileResponse(
    val fileName: String? = null,
    val cause: String? = null,
    val status: HttpStatus,
    val success: Boolean
) : ResponseEntity<String>(
    fileName,
    status
)
