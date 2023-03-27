package com.beside.daldal.domain.auth.service

import com.beside.daldal.domain.auth.dto.TokenResponseDTO
import com.beside.daldal.domain.auth.error.BearerTokenIsNotRefreshToken
import com.beside.daldal.domain.auth.error.JwtNotFoundException
import com.beside.daldal.domain.member.dto.MemberCreateDTO
import com.beside.daldal.domain.member.dto.MemberLoginDTO
import com.beside.daldal.domain.member.repository.MemberRepository
import com.beside.daldal.domain.member.service.MemberService
import com.beside.daldal.jwt.JwtTokenProvider
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val memberService: MemberService,
    private val jwtTokenProvider: JwtTokenProvider,
    private val stringRedisTemplate: StringRedisTemplate
) {
    @Transactional
    fun login(dto: MemberLoginDTO): TokenResponseDTO {
        val member = memberRepository.findByEmail(dto.email)

        if (member == null)
            memberService.create(MemberCreateDTO(dto.loginType, dto.email))

        val token =  jwtTokenProvider.generateTokenDto(
            UsernamePasswordAuthenticationToken(
                dto.email, "", listOf(SimpleGrantedAuthority("ROLE_USER"))
            )
        )

        val oper = stringRedisTemplate.opsForValue()
        oper[dto.email] = token.refreshToken
        return token
    }


    fun logout(email :String) = stringRedisTemplate.opsForValue().getAndDelete(email)

    fun reissue(email :String, bearer : String): TokenResponseDTO {
        println(bearer)
        val token = bearer.substring("Bearer ".length)

        val oper=  stringRedisTemplate.opsForValue()
        val refresh = oper[email] ?: throw JwtNotFoundException()
        if(refresh != token) throw BearerTokenIsNotRefreshToken()

        val dto =  jwtTokenProvider.generateTokenDto(
            UsernamePasswordAuthenticationToken(
                email, "", listOf(SimpleGrantedAuthority("ROLE_USER"))
            )
        )
        oper[email] = dto.refreshToken
        return dto
    }
}
