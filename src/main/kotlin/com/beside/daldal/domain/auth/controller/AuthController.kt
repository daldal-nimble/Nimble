package com.beside.daldal.domain.auth.controller

import com.beside.daldal.domain.auth.dto.TokenResponseDTO
import com.beside.daldal.domain.auth.error.JwtNotFoundException
import com.beside.daldal.domain.auth.service.AuthService
import com.beside.daldal.domain.member.dto.MemberLoginDTO
import com.beside.daldal.domain.member.service.MemberService
import com.beside.daldal.shared.dto.CommonResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService,
    private val memberService: MemberService
) {

    @PostMapping("/login")
    fun login(@RequestBody dto: MemberLoginDTO): ResponseEntity<TokenResponseDTO> =
        ResponseEntity.ok(authService.login(dto))

    @PostMapping("/logout")
    fun logout(principal: Principal, req: HttpServletRequest): ResponseEntity<CommonResponse> {
        val email = principal.name
        authService.logout(email)
        return ResponseEntity.ok(CommonResponse(message = "logout success", code = "LOGOUT_SUCCESS", status = 200))
    }

    @PostMapping("/signout")
    fun signOut(principal: Principal): ResponseEntity<CommonResponse> {
        val email = principal.name
        memberService.deleteByEmail(email)
        return ResponseEntity.ok(
            CommonResponse(
                message = "성공적으로 회원 정보가 삭제되었습니다.",
                code = "MEMBER_SIGN_OUT",
                status = 200
            )
        )
    }

    @PostMapping("/reissue")
    fun reissue(principal: Principal, req : HttpServletRequest): ResponseEntity<TokenResponseDTO> {
        val email = principal.name
        val bearer = req.getHeader("Authorization")
        return ResponseEntity.ok(authService.reissue(email, bearer))
    }
}