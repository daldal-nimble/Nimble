package com.beside.daldal.domain.auth.dto

import com.beside.daldal.domain.member.dto.MemberReadDTO

class LoginSuccessDTO (
    val token : TokenResponseDTO,
    val member : MemberReadDTO
)