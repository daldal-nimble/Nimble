package com.beside.daldal.domain.favourite.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class FavouriteNotFoundException:DaldalRuntimeException(
    ErrorCode(
        message = "bookmark error",
        code = "BOOK_MARK_ERROR",
        status = 404
    )
) {
}