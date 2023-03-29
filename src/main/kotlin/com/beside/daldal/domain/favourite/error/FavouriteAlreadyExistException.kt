package com.beside.daldal.domain.favourite.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class FavouriteAlreadyExistException:DaldalRuntimeException(
    ErrorCode(
        message = "bookmark already exist",
        code = "BOOKMARK_ALREADY_EXIST",
        status = 409
    )
) {
}