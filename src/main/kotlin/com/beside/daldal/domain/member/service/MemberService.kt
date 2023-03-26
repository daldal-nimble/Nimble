package com.beside.daldal.domain.member.service

import com.beside.daldal.domain.member.dto.MemberCreateDTO
import com.beside.daldal.domain.member.entity.Member
import com.beside.daldal.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun create(dto: MemberCreateDTO): Member = memberRepository.save(dto.toEntity())
}