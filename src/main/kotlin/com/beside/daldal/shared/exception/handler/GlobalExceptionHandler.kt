package com.beside.daldal.shared.exception.handler

import com.beside.daldal.shared.exception.dto.DaldalRuntimeException
import com.beside.daldal.shared.exception.dto.ErrorCode
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(DaldalRuntimeException::class)
    fun daldalException(exception: DaldalRuntimeException): ResponseEntity<ErrorCode> =
        ResponseEntity.status(HttpStatusCode.valueOf(exception.errorCode.status)).body(exception.errorCode)
}