package com.example.services.queries

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty

data class GetFileRequest(
    var fileName: String,
    var fileFolder: String,
    var toPath: String
)

@JsonAutoDetect
data class GetFileResponse(
    @JsonProperty("byteArray") val byteArray: ByteArray? = null,
    @JsonProperty("cause") val cause: String? = null,
    @JsonProperty("success") val success: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as GetFileResponse

        if (byteArray != null) {
            if (other.byteArray == null) return false
            if (!byteArray.contentEquals(other.byteArray)) return false
        } else if (other.byteArray != null) return false
        if (cause != other.cause) return false
        if (success != other.success) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (byteArray?.contentHashCode() ?: 0)
        result = 31 * result + (cause?.hashCode() ?: 0)
        result = 31 * result + success.hashCode()
        return result
    }
}