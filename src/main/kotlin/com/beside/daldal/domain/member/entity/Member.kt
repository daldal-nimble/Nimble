package com.beside.daldal.domain.member.entity

import com.beside.daldal.shared.entity.BaseTimeEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("member")
class Member(
    @Id
    var id:String? = null,
    var email: String,
    var loginType: LoginType,
    var authrity : Authority = Authority.ROLE_USER,
    var isRun : MutableList<String> = mutableListOf(),
    var isNotRun : MutableList<String> = mutableListOf(),

    var username :String? = null,
    var nickname :String? = null,
    var gender :String? = null,
) : BaseTimeEntity() {
    enum class Authority {
        ROLE_USER, ROLE_ADMIN
    }
    fun delete(){
        deletedAt = LocalDateTime.now()
    }
    fun  getCourseIds() : List<String> = isRun + isNotRun
}