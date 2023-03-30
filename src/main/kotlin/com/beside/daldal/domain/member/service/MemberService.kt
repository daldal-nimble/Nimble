package com.beside.daldal.domain.member.service

import com.beside.daldal.domain.member.dto.MemberCreateDTO
import com.beside.daldal.domain.member.entity.Member
import com.beside.daldal.domain.member.error.MemberNotFoundException
import com.beside.daldal.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun create(dto: MemberCreateDTO): Member = memberRepository.save(dto.toEntity())

    @Transactional
    fun deleteByEmail(email :String){
        val memberId : String = memberRepository.findByEmail(email)?.id ?: throw MemberNotFoundException()
        memberRepository.deleteById(memberId)
    }
}