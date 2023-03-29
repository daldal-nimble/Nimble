package com.beside.daldal.domain.sentiment.service

import com.beside.daldal.domain.sentiment.dto.Document
import com.beside.daldal.domain.sentiment.dto.SentimentRequest
import com.beside.daldal.domain.sentiment.dto.SentimentResponse
import com.beside.daldal.domain.sentiment.error.SentimentAnalyzeFailException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class SentimentService(
    private val webClient: WebClient
) {
    val url = "https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze"

    @Value("\${sentiment.clientKey}")
    private lateinit var clientKey: String
    @Value("\${sentiment.secretKey}")
    private lateinit var secretKey: String

    fun analyze(content: String): Document {
        val res = webClient.post()
            .uri(url)
            .headers { headers ->
                headers["X-NCP-APIGW-API-KEY-ID"] = clientKey
                headers["X-NCP-APIGW-API-KEY"] = secretKey
            }
            .bodyValue(SentimentRequest(content))
            .retrieve()
            .toEntity(SentimentResponse::class.java)
            .block()
            ?.body ?: throw SentimentAnalyzeFailException()

        return res.document
    }
}