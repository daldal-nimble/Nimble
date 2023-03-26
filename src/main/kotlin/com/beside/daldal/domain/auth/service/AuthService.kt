package com.beside.daldal.domain.auth.service

import com.beside.daldal.domain.auth.dto.LoginDTO
import com.beside.daldal.domain.member.dto.MemberCreateDTO
import com.beside.daldal.domain.member.dto.MemberLoginDTO
import com.beside.daldal.domain.member.repository.MemberRepository
import com.beside.daldal.domain.member.service.MemberService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val memberService: MemberService
) {
    @Transactional
    fun login(dto: MemberLoginDTO): LoginDTO {
        val member = memberRepository.findByUid(dto.uid)

        if (member == null) {
            memberService.create(
                MemberCreateDTO(
                    dto.uid,
                    dto.email,
                    dto.loginType
                )
            )
            return LoginDTO("", "", 0L, 0L)
        }

        // token generate

        return LoginDTO("", "", 0L, 0L)
    }
}