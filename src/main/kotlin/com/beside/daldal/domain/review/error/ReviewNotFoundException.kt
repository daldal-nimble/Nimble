package com.beside.daldal.domain.review.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class ReviewNotFoundException : DaldalRuntimeException(
    ErrorCode(
        message = "review not found",
        code = "REVIEW_NOT_FOUND",
        status = 404
    )
) {
}
