package com.example.services.queries

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@JsonAutoDetect
data class DeleteFileRequest(
    @JsonProperty("fileName") var fileName: String,
    @JsonProperty("fileFolder") var folderName: String
)

data class DeleteFileResponse(
    val cause: String? = null,
    val status: HttpStatus,
    val success: Boolean
) : ResponseEntity<Boolean>(
    success,
    status
)