package com.example.services.queries

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty

@JsonAutoDetect
data class BaseResponse(
    @JsonProperty("message") val message: String,
    @JsonProperty("success") val success: Boolean
)