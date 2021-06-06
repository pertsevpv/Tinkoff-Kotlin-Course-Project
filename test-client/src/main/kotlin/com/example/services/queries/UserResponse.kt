package com.example.services.queries

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty

@JsonAutoDetect
data class UserResponse(
    @JsonProperty("message") val message: String,
    @JsonProperty("status") val status: Boolean,
    @JsonProperty("userId") val userId: Int? = null,
    @JsonProperty("hash") val hash: String? = null
)