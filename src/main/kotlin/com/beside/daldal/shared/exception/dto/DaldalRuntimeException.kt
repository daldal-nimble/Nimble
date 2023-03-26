package com.beside.daldal.shared.exception.dto

open class DaldalRuntimeException(
    val errorCode: ErrorCode
) : RuntimeException()