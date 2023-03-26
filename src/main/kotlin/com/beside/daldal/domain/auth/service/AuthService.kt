package com.beside.daldal.domain.auth.service

import com.beside.daldal.domain.auth.dto.TokenResponseDTO
import com.beside.daldal.domain.member.dto.MemberCreateDTO
import com.beside.daldal.domain.member.dto.MemberLoginDTO
import com.beside.daldal.domain.member.repository.MemberRepository
import com.beside.daldal.domain.member.service.MemberService
import com.beside.daldal.jwt.JwtTokenProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val memberService: MemberService,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional
    fun login(dto: MemberLoginDTO): TokenResponseDTO {
        val member = memberRepository.findByUid(dto.uid)

        // member가 없으면 생성
        if (member == null) {
            memberService.create(
                MemberCreateDTO(
                    dto.uid,
                    dto.email,
                    dto.loginType
                )
            )
//            return TokenResponseDTO("", "", 0L, 0L)
        }
        // token generate

        return jwtTokenProvider.generateTokenDto(
            UsernamePasswordAuthenticationToken(
                dto.uid, "",
                listOf(SimpleGrantedAuthority("ROLE_USER"))
            )
        )

        // refresh token generate
        // save
    }
}
