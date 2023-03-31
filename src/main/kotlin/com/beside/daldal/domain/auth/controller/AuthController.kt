package com.beside.daldal.domain.auth.controller

import com.beside.daldal.domain.auth.dto.LoginSuccessDTO
import com.beside.daldal.domain.auth.dto.TokenResponseDTO
import com.beside.daldal.domain.auth.error.JwtNotFoundException
import com.beside.daldal.domain.auth.service.AuthService
import com.beside.daldal.domain.member.dto.MemberLoginDTO
import com.beside.daldal.domain.member.service.MemberService
import com.beside.daldal.shared.dto.CommonResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService,
    private val memberService: MemberService
) {

    @Operation(
        operationId = "login",
        summary = "로그인",
        description = "로그인",
        tags = ["auth"]
    )
    @PostMapping("/login")
    fun login(@RequestBody dto: MemberLoginDTO): ResponseEntity<LoginSuccessDTO> =
        ResponseEntity.ok(authService.login(dto))

    @Operation(
        operationId = "logout",
        summary = "로그아웃",
        description = "로그아웃",
        tags = ["auth"]
    )
    @PostMapping("/logout")
    fun logout(principal: Principal, req: HttpServletRequest): ResponseEntity<CommonResponse> {
        val email = principal.name
        authService.logout(email)
        return ResponseEntity.ok(CommonResponse(message = "logout success", code = "LOGOUT_SUCCESS", status = 200))
    }

    @Operation(
        operationId = "signOut",
        summary = "회원 탈퇴",
        description = "회원 탈퇴",
        tags = ["auth"]
    )
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

    @Operation(
        operationId = "reissue",
        summary = "토큰 재발급",
        description = "헤더에 refresh token을 넣으면 재발급 됩니다.",
        tags = ["auth"]
    )
    @PostMapping("/reissue")
    fun reissue(principal: Principal, req : HttpServletRequest): ResponseEntity<TokenResponseDTO> {
        val email = principal.name
        val bearer = req.getHeader("Authorization")
        return ResponseEntity.ok(authService.reissue(email, bearer))
    }
}