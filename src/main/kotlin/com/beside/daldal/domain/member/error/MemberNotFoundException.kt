package com.beside.daldal.domain.member.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class MemberNotFoundException : DaldalRuntimeException(
    ErrorCode(
        message = "member not found",
        code = "MEMBER_NOT_FOUND",
        status = 404
    )
) {
}