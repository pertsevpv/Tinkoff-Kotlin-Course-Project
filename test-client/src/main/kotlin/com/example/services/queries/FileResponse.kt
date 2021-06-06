package com.example.services.queries

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty

@JsonAutoDetect
class FileResponse(
    @JsonProperty("message") val message: String? = null,
    @JsonProperty("status") val status: Boolean
)