package com.beside.daldal.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val jwtTokenProvider: JwtTokenProvider,
) : OncePerRequestFilter() {

    private val AUTHORIZATION_HEADER = "Authorization"
    private val BEARER_PREFIX = "Bearer "
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        logger.info("===path, method===")
        logger.info("path : ${request.servletPath}")
        logger.info("method : ${request.method}")
        logger.info("===header===")
        for (name in request.headerNames) {
            logger.info("$name : ${request.getHeader(name)}")
        }
        logger.info("===part===")
        if (request.contentType == "multipart/form-data") {
            val parts = request.parts
            for (part in parts) {
                logger.info("${part.name} : ${part.size}")
            }
        }

        if(request.getHeader(AUTHORIZATION_HEADER) == null){
            filterChain.doFilter(request, response)
            return
        }

        val token = resolveToken(request) ?: throw RuntimeException("토큰이 존재하지 않습니다.")

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            val authentication = jwtTokenProvider.getAuthentication(token)
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