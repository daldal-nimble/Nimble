package com.beside.daldal.domain.member.dto

import com.beside.daldal.domain.member.entity.LoginType

class MemberLoginDTO(
    val loginType: LoginType,
    val email:String
)