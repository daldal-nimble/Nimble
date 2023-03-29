package com.beside.daldal.domain.image.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class ImageNotFoundException:DaldalRuntimeException(
    ErrorCode(
        message = "image not found",
        code = "IMAGE_NOT_FOUND",
        status = 404
    )
) {
}