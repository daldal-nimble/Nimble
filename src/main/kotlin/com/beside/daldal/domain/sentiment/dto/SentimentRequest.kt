package com.beside.daldal.domain.sentiment.dto

import com.beside.daldal.domain.sentiment.error.SentimentMaxLengthException


data class SentimentRequest(val content: String) {
    init {
        if (content.length > 1000) throw SentimentMaxLengthException()
    }
}