package com.beside.daldal.domain.auth.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class JwtNotFoundException : DaldalRuntimeException(
    ErrorCode(message = "jwt token not found", code = "BAD_REQUEST", status = 400)
) {
}