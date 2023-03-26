package com.beside.daldal.domain.member.dto

import com.beside.daldal.domain.member.entity.LoginType
import com.beside.daldal.domain.member.entity.Member


class MemberReadDTO (
    val uid:String,
    val email :String,
    val loginType:LoginType,
    val username :String?
){
    companion object{
        fun from(entity : Member):MemberReadDTO{
            return MemberReadDTO(
                uid = entity.uid,
                email = entity.email,
                loginType = entity.loginType,
                username = entity.username,
            )
        }
    }
}