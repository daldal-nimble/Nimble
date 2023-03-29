package com.beside.daldal.domain.image.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.beside.daldal.domain.image.error.ImageDeleteFailException
import com.beside.daldal.domain.image.error.ImageUploadFailException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ImageService(private val s3: AmazonS3) {
    @Value("\${ncp.bucket.name}")
    private lateinit var bucketName: String

    @Value("\${ncp.bucket.host}")
    private lateinit var bucketHost: String

    fun upload(
        multipartFile: MultipartFile,
        prefix: String = ""
    ): String {
        try {
            val originalName = "$prefix/${multipartFile.originalFilename}" // 파일 이름
            val size = multipartFile.size // 파일 크기
            val objectMetaData = ObjectMetadata()
            objectMetaData.contentType = multipartFile.contentType
            objectMetaData.contentLength = size
            s3.putObject(
                PutObjectRequest("daldal-bucket", originalName, multipartFile.inputStream, objectMetaData)
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            )
            return "$bucketHost/$bucketName/$originalName"
        } catch (e: Exception) {
            throw ImageUploadFailException()
        }
    }

    fun delete(
        filename: String,
        prefix: String
    ) {
        try {
            s3.deleteObject(bucketName, "$prefix/$filename")
        } catch (e: Exception) {
            throw ImageDeleteFailException()
        }
    }
}