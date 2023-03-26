package com.beside.daldal.domain.member.dto

import com.beside.daldal.domain.member.entity.LoginType

class MemberLoginDTO(
    val uid : String,
    val email:String,
    val loginType: LoginType
)