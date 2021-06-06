package com.example.services

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.S3Object
import com.example.configs.AWSConfigs
import com.example.database.entities.FileStatus
import com.example.database.services.FileService
import com.example.database.services.FileStatusService
import com.example.database.services.UserService
import com.example.services.queries.*
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files

@Component("awsService")
class AWSS3Service(
    @Autowired @Qualifier("s3Client") private val s3Client: AmazonS3,
    @Autowired @Qualifier("awsConfig") private val config: AWSConfigs
) {

    fun getBucketName(): String = config.bucketName() ?: throw Exception("Can't get bucket name")

    fun uploadFile(req: UploadFileRequest): String {
        return try {
            if (FileService().contains(req.name, req.folder))
                JSONObject()
                    .put("message", "Failed\nFile ${req.name} already exists")
                    .put("success", false)
                    .toString()
            else {
                val uplFile = writeTmpFile(req.byteArray, req.name)!!
                val path = "${req.folder}/${req.name}"
                val uploadReq = PutObjectRequest(
                    getBucketName(),
                    path,
                    uplFile
                )
                s3Client.putObject(uploadReq)
                val user = UserService().findByName(req.folder)!!
                val file = FileService().save(com.example.database.entities.File(req.name, req.folder))
                FileStatusService().save(FileStatus(user.id!!, file.id!!, user.id!!, req.status))
                uplFile.delete()
                JSONObject()
                    .put("message", "${req.name} uploaded to ${req.folder}")
                    .put("success", true)
                    .toString()
            }
        } catch (e: Exception) {
            JSONObject()
                .put("message", "Failed\n${e.message}")
                .put("success", false)
                .toString()
        }
    }

    fun deleteFile(req: DeleteFileRequest): String {
        val path = req.folderName.plus("/").plus(req.fileName)
        return try {
            val deleteObjectRequest = DeleteObjectRequest(getBucketName(), path)
            s3Client.deleteObject(deleteObjectRequest)
            val file = FileService().delete(FileService().findByFileAndFolder(req.fileName, req.folderName)!!)
            FileStatusService().deleteByFileId(file.id!!)
            JSONObject()
                .put("message", "${req.fileName} deleted")
                .put("success", true)
                .toString()
        } catch (e: Exception) {
            JSONObject()
                .put("message", "Failed\n${e.message}")
                .put("success", false)
                .toString()
        }
    }

    fun downloadFile(req: GetFileRequest): String {
        return try {
            val filePath = "${req.fileFolder}/${req.fileName}"
            val s3Object: S3Object = s3Client.getObject(GetObjectRequest(getBucketName(), filePath))
            val json = JSONObject()
                .put("byteArray", s3Object.objectContent.readBytes())
                .put("success", true)
            json.toString()

        } catch (e: Exception) {
            val json = JSONObject()
                .put("cause", e.message)
                .put("success", false)
            json.toString()
        }
    }

    fun writeTmpFile(byteArray: ByteArray, fileName: String): File? {
        val tmpFile = File("tmp/$fileName")
        Files.createDirectories(tmpFile.toPath().parent)
        tmpFile.outputStream().use { stream ->
            runCatching {
                stream.write(byteArray)
                return tmpFile
            }.onSuccess {
                return tmpFile
            }.onFailure {
                throw it
            }
        }
        return null
    }
}