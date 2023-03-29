package com.beside.daldal.domain.image.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class ImageDeleteFailException : DaldalRuntimeException(
    ErrorCode(
        message = "이미지를 삭제하는 도중 문제가 발생했습니다.",
        status = 500,
        code = "IMAGE_DELETE_FAIL"
    )
) {
}