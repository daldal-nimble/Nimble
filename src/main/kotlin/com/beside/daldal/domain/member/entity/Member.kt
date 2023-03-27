package com.beside.daldal.domain.member.entity

import com.beside.daldal.shared.entity.BaseTimeEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

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
) : BaseTimeEntity() {
    enum class Authority {
        ROLE_USER, ROLE_ADMIN
    }
    fun delete(){
        deletedAt = LocalDateTime.now()
    }
}