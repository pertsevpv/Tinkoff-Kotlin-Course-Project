package com.example.configs

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component("awsConfig")
@Configuration
class AWSConfigs {

    companion object {
        private val context = AnnotationConfigApplicationContext(AWSProperty::class.java)
        val instance: AWSProperty = context.getBean("awsProperties", AWSProperty::class.java)
    }

    @Bean("s3Client")
    fun s3Client(
        @Autowired @Qualifier("awsAccessKey") accessKey: String,
        @Autowired @Qualifier("awsSecretKey") secretKey: String,
        @Autowired @Qualifier("awsRegion") region: String
    ): AmazonS3 {
        return AmazonS3ClientBuilder.standard()
            .withCredentials(
                AWSStaticCredentialsProvider(
                    BasicAWSCredentials(
                        accessKey,
                        secretKey
                    )
                )
            )
            .withRegion(region)
            .build()
    }

    @Bean("awsBucketName")
    fun bucketName() = instance.bucketName

    @Bean("awsAccessKey")
    fun accessKey() = instance.accessKey

    @Bean("awsSecretKey")
    fun secretKey() = instance.secretKey

    @Bean("awsRegion")
    fun region() = instance.region

    @Bean("maxFileSize")
    fun maxFileSize() = instance.maxFileSize
}
