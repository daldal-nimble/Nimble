package com.beside.daldal.domain.course.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class CourseExistAlreadyException:DaldalRuntimeException(
    ErrorCode(
        message = "해당하는 값이 이미 추가 되어 있습니다.",
        status = 400,
        code = "ALREADY_EXIST"
    )
) {
}