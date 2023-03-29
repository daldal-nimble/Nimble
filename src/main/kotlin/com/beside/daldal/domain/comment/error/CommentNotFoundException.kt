package com.beside.daldal.domain.comment.error

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode

class CommentNotFoundException : DaldalRuntimeException(
    ErrorCode(
        message = "comment not found",
        code = "COMMENT_NOT_FOUND",
        status = 404
    )
) {
}