package com.example.services.queries

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus

data class DeleteFileRequest(
    var fileName:String,
    var folderName: String,
)

@JsonAutoDetect
data class DeleteFileResponse(
    @JsonProperty("cause") val cause: String? = null,
    @JsonProperty("status") val status: HttpStatus,
    @JsonProperty("success") val success: Boolean
)