package com.example.services.queries

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@JsonAutoDetect
data class StringListGetRequest(
    @JsonProperty("folderName") var folderName: String
)

data class StringListGetResponse(
    val list: List<String>? = null,
    val cause: String? = null,
    val status: HttpStatus,
    val success: Boolean
) : ResponseEntity<List<String>>(
    list,
    status
)
