package com.example.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.*
import org.springframework.stereotype.Component

private const val BUCKET_NAME = "amazon.s3.bucket.name"
private const val S3_ACCESS_KEY = "amazon.s3.accessKey"
private const val S3_SECRET_KEY = "amazon.s3.secretKey"
private const val REGION = "amazon.region"
private const val MAX_FILE_SIZE = "max.file.size"

@Component("awsProperties")
@Configuration
@PropertySource("classpath:aws.properties")
class AWSProperty {

    @Value("\${$BUCKET_NAME}")
    var bucketName: String? = null

    @Value("\${$S3_ACCESS_KEY}")
    var accessKey: String? = null

    @Value("\${$S3_SECRET_KEY}")
    var secretKey: String? = null

    @Value("\${$REGION}")
    var region: String? = null

    @Value("\${$MAX_FILE_SIZE}")
    var maxFileSize: Long? = null

}