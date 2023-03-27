package com.beside.daldal.domain.course.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class CourseAuthorizationException : DaldalRuntimeException(
    ErrorCode(
        message = "해당 코스에 대한 권한이 없습니다.",
        code = "COURSE_NOT_AUTHORIZATION",
        status = 401
    )
) {
}