package com.beside.daldal.domain.course.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class CourseNotFoundException : DaldalRuntimeException(
    ErrorCode(
        message = "course not found",
        code = "COURSE_NOT_FOUND",
        status = 404
    )
) {
}