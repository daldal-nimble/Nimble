package com.beside.daldal.jwt

import com.beside.daldal.domain.auth.dto.TokenResponseDTO
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.stream.Collectors

@Component
class JwtTokenProvider(

) {
    private val AUTHORITIES_KEY = "auth"
    private lateinit var key: Key
    private val ACCESS_TOKEN_VALIDITY_SECONDS: Long = 20 * 60 * 1000
    private val REFRESH_TOKEN_VALIDITY_SECONDS: Long = 2 * 60 * 60 * 1000
    @Value("\${jwt.secret.key}")
    private lateinit var secretKey: String

    @PostConstruct
    protected fun init() {
        val keyBytes = Base64.getDecoder().decode(secretKey)
        key = Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateTokenDto(authentication: Authentication): TokenResponseDTO {
        // 권한들 가져오기
        val authorities = authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))
        val now = System.currentTimeMillis()

        // Access Token 생성
        val accessTokenExpiresIn: Date = Date(now + ACCESS_TOKEN_VALIDITY_SECONDS)
        val accessToken = Jwts.builder()
            .setSubject(authentication.name) // payload "sub": "name"
            .claim(AUTHORITIES_KEY, authorities) // payload "auth": "ROLE_USER"
            .setExpiration(accessTokenExpiresIn) // payload "exp": 1516239022 (예시)
            .signWith(key, SignatureAlgorithm.HS512) // header "alg": "HS512"
            .compact()

        // Refresh Token 생성
        val refreshTokenExpiresIn = Date(now + REFRESH_TOKEN_VALIDITY_SECONDS)
        val refreshToken = Jwts.builder()
            .setSubject(authentication.name) // payload "sub": "name"
            .claim(AUTHORITIES_KEY, authorities) // payload "auth": "ROLE_USER"
            .setExpiration(refreshTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
        return TokenResponseDTO(accessToken, refreshToken, accessTokenExpiresIn.time, refreshTokenExpiresIn.time)
    }

    fun getAuthentication(accessToken: String): Authentication {
        // token decryption
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(accessToken)
            .body

        // getAuthorities from claim
        val authorities = claims[AUTHORITIES_KEY].toString().split(",").stream()
            .map { obj: String -> SimpleGrantedAuthority(obj) }
            .collect(Collectors.toList())

        // UserDetails 객체를 만들어서 Authentication 리턴
        val principal = User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, accessToken, authorities)
    }


    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            throw RuntimeException("Expired or invalid JWT token")
        }
    }
}

