package com.example.services.queries

data class UploadFileRequest(
    var byteArray: ByteArray,
    var folder: String,
    var name: String,
    var status: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UploadFileRequest

        if (!byteArray.contentEquals(other.byteArray)) return false
        if (folder != other.folder) return false
        if (name != other.name) return false
        if (status != other.status) return false

        return true
    }

    override fun hashCode(): Int {
        var result = byteArray.contentHashCode()
        result = 31 * result + folder.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + status.hashCode()
        return result
    }
}