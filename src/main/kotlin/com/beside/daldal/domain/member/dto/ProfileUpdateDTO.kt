package com.beside.daldal.domain.member.dto

import com.beside.daldal.domain.member.entity.Gender

class ProfileUpdateDTO (
    val username: String?,
    val nickname: String?,
    var gender: Gender,
)