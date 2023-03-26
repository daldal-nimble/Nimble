package com.beside.daldal.domain.auth.dto

class TokenResponseDTO(
    val accessToken :String,
    val refreshToken : String,
    val accessExpiration : Long,
    val refreshExpiration : Long
)