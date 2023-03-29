package com.beside.daldal.domain.sentiment.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class SentimentAnalyzeFailException:DaldalRuntimeException(
    ErrorCode(
        message = "감정 분석 도중 문제가 발행했습니다.",
        code = "SENTIMENT_ANALYZE_ERROR",
        status = 500
    )
) {
}