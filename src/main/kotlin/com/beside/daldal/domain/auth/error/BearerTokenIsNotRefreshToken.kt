package com.beside.daldal.domain.auth.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode


class BearerTokenIsNotRefreshToken : DaldalRuntimeException(
    ErrorCode(
        message = "refresh token error",
        code = "REFRESH_TOKEN_ERROR",
        status = 400
    )
) {
}