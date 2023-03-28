package com.beside.daldal.domain.review.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class ReviewAuthorizationException : DaldalRuntimeException(
    ErrorCode(
        message = "해당 리뷰에 대한 권한이 없습니다.",
        code = "REVIEW_NOT_AUTHORIZATION",
        status = 401
    )
) {

}
