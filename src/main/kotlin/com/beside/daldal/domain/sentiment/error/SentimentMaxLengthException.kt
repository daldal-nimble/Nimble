package com.beside.daldal.domain.sentiment.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class SentimentMaxLengthException:DaldalRuntimeException(
    ErrorCode(
        message = "1000자를 초과할 수 없습니다.",
        code = "SENTIMENT_MAX_LENGTH",
        status = 400
    )
) {
}