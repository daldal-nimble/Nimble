package com.beside.daldal.domain.image.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class ImageTypeException:DaldalRuntimeException(
    ErrorCode(
        message = "이미지의 형식이 jpg, jpeg, png, svg 와 다릅니다.",
        code = "IMAGE_EXTENSION_TYPE_NOT_ALLOW",
        status = 400
    )
) {
}