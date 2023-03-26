package com.beside.daldal.domain.member.repository

import com.beside.daldal.domain.member.entity.Member
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : MongoRepository<Member, String> {
    fun findByUid(uid: String): Member?
}