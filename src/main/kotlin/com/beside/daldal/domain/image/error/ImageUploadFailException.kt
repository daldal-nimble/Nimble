package com.beside.daldal.domain.image.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class ImageUploadFailException : DaldalRuntimeException(
    ErrorCode(
        message = "이미지를 업로드 하는 도중에 문제가 발생했습니다.",
        status = 500,
        code = "IMAGE_UPLOAD_FAIL"
    )
) {
}