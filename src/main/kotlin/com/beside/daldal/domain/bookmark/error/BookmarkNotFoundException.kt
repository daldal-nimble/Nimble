package com.beside.daldal.domain.bookmark.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class BookmarkNotFoundException:DaldalRuntimeException(
    ErrorCode(
        message = "bookmark error",
        code = "BOOK_MARK_ERROR",
        status = 404
    )
) {
}