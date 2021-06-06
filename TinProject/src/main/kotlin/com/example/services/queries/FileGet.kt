package com.example.services.queries

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.io.InputStream

@JsonAutoDetect
data class GetFileRequest(
    @JsonProperty("fileName") var fileName: String,
    @JsonProperty("fileFolder") var fileFolder: String,
    @JsonProperty("toPath")  var toPath: String
)