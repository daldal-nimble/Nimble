package com.beside.daldal.domain.member.dto

import com.beside.daldal.domain.member.entity.LoginType
import com.beside.daldal.domain.member.entity.Member

class MemberCreateDTO(
    val uid: String,
    val email: String,
    val loginType : LoginType
){
    fun toEntity()  : Member =
        Member(
            uid = uid,
            email = email,
            loginType = loginType
        )
}