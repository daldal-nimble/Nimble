package com.beside.daldal.domain.sentiment.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class SentimentResponse(
    val document: Document
)

class Document(
    val sentiment: String,
    val confidence: Confidence
)

class Confidence(
    val negative: Double,
    val positive: Double,
    val neutral: Double,
)