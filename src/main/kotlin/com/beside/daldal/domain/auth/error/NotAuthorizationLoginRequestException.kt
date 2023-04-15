package com.beside.daldal.domain.auth.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class NotAuthorizationLoginRequestException: DaldalRuntimeException(
    ErrorCode(
        message = "허용되지 않는 로그인 요청입니다.",
        code = "NOT_AUTHORIZATION",
        status = 400
    )
) {
}