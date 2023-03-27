package com.beside.daldal.domain.member.dto

import com.beside.daldal.domain.member.entity.LoginType
import com.beside.daldal.domain.member.entity.Member

class MemberCreateDTO(
    val loginType : LoginType,
    val email: String
){
    fun toEntity()  : Member =
        Member(
            email = email,
            loginType = loginType
        )
}