package com.beside.daldal.domain.member.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("member")
class Member(
    @Id
    var id:String? = null,
    var loginType: LoginType,
    var email: String,
    var username :String? = null,
    var nickname :String? = null,
    var gender :String? = null,

    // role
    var authrity : Authority = Authority.ROLE_USER
) {
    enum class Authority {
        ROLE_USER, ROLE_ADMIN
    }
}