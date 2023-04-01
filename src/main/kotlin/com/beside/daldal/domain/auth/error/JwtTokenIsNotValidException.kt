package com.beside.daldal.domain.auth.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class JwtTokenIsNotValidException:DaldalRuntimeException(
    ErrorCode(
        message = "jwt is not valid",
        code= "JWT_NOT_VALID",
        status = 400
    )
) {
}