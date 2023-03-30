package com.beside.daldal.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(private var jwtTokenProvider: JwtTokenProvider) : OncePerRequestFilter() {

    private val AUTHORIZATION_HEADER = "Authorization"
    private val BEARER_PREFIX = "Bearer "
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 헤더에서 토큰을 받아오기
        val token = resolveToken(request) ?: throw RuntimeException("토큰이 존재하지 않습니다.")

        // 유효한 토큰인지 확인
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            // 토큰으로부터 유저 정보를 받아옴
            val authentication = jwtTokenProvider.getAuthentication(token)
            // SecurityContext에 Authentication 객체를 저장

            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7)
        }
        return null
    }
}