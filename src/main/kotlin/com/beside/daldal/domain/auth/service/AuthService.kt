package com.beside.daldal.domain.auth.service

import com.beside.daldal.domain.auth.dto.LoginSuccessDTO
import com.beside.daldal.domain.auth.dto.TokenResponseDTO
import com.beside.daldal.domain.auth.error.BearerTokenIsNotRefreshToken
import com.beside.daldal.domain.auth.error.JwtNotFoundException
import com.beside.daldal.domain.auth.error.JwtTokenIsNotValidException
import com.beside.daldal.domain.auth.error.NotAuthorizationLoginRequestException
import com.beside.daldal.domain.member.dto.MemberCreateDTO
import com.beside.daldal.domain.member.dto.MemberLoginDTO
import com.beside.daldal.domain.member.dto.MemberReadDTO
import com.beside.daldal.domain.member.error.MemberNotFoundException
import com.beside.daldal.domain.member.repository.MemberRepository
import com.beside.daldal.domain.member.service.MemberService
import com.beside.daldal.jwt.JwtTokenProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.Principal

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val memberService: MemberService,
    private val jwtTokenProvider: JwtTokenProvider,
    private val stringRedisTemplate: StringRedisTemplate
) {
    @Value("\${daldal.secret}")
    private lateinit var secretKey: String

    @Transactional
    fun login(dto: MemberLoginDTO): LoginSuccessDTO {
        // 프론트 서버와의 통신이 맞는지 확인 절차
        if (!isValid(dto.secretKey))
            throw NotAuthorizationLoginRequestException()

        // 실제 로그인 부분
        var member = memberRepository.findByEmail(dto.email)

        if (member == null)
            member = memberService.create(MemberCreateDTO(dto.loginType, dto.email))

        val token = jwtTokenProvider.generateTokenDto(
            UsernamePasswordAuthenticationToken(
                dto.email, "", listOf(SimpleGrantedAuthority("ROLE_USER"))
            )
        )

        val oper = stringRedisTemplate.opsForValue()
        oper[dto.email] = token.refreshToken

        return LoginSuccessDTO(token, MemberReadDTO.from(member))
    }


    fun logout(email: String) = stringRedisTemplate.opsForValue().getAndDelete(email)

    fun reissue(refreshToken: String, secretKey: String): LoginSuccessDTO {
        if (!isValid(secretKey))
            throw NotAuthorizationLoginRequestException()

        if (!jwtTokenProvider.validateToken(refreshToken))
            throw JwtTokenIsNotValidException()

        val authentication = jwtTokenProvider.getAuthentication(refreshToken)

        val user = authentication.principal as User
        val email = user.username
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()

        val oper = stringRedisTemplate.opsForValue()
        val savedRefreshToken = oper[email] ?: throw JwtNotFoundException()
        if (savedRefreshToken != refreshToken) throw BearerTokenIsNotRefreshToken()

        val dto = jwtTokenProvider.generateTokenDto(
            UsernamePasswordAuthenticationToken(
                email, "", listOf(SimpleGrantedAuthority("ROLE_USER"))
            )
        )
        oper[email] = dto.refreshToken
        return LoginSuccessDTO(token = dto, member = MemberReadDTO.from(member))
    }

    fun isValid(secretKey: String) = this.secretKey == secretKey
}
