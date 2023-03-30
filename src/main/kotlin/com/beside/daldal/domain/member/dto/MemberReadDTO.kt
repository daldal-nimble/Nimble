package com.beside.daldal.domain.member.dto

import com.beside.daldal.domain.member.entity.Gender
import com.beside.daldal.domain.member.entity.LoginType
import com.beside.daldal.domain.member.entity.Member


class MemberReadDTO(
    val id: String?,
    val loginType: LoginType,
    val email: String,
    val profileImageUrl: String,
    val username: String?,
    val nickname: String?,
    var gender: Gender,
) {
    companion object {
        fun from(entity: Member): MemberReadDTO {
            return MemberReadDTO(
                id = entity.id,
                loginType = entity.loginType,
                email = entity.email,
                username = entity.username,
                nickname = entity.nickname,
                profileImageUrl = entity.profileImageUrl,
                gender = entity.gender
            )
        }
    }
}