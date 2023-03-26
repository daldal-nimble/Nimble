package com.beside.daldal.domain.auth.controller

import com.beside.daldal.domain.auth.dto.TokenResponseDTO
import com.beside.daldal.domain.auth.service.AuthService
import com.beside.daldal.domain.member.dto.MemberLoginDTO
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody dto: MemberLoginDTO): ResponseEntity<TokenResponseDTO> = ResponseEntity.ok(authService.login(dto))

    @PostMapping("/logout")
    fun logout() {

    }

    @PostMapping("/signout")
    fun signOut() {

    }

    @PostMapping("/reissue")
    fun reissue() {

    }
}