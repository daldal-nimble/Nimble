package com.beside.daldal.domain.member.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("member")
class Member(
    @Id
    var id:String? = null,
    var uid:String,//provider
    var loginType : LoginType,
    var email:String,
    var username :String? = null
)