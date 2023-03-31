package com.beside.daldal.domain.member.controller

import com.beside.daldal.domain.member.dto.MemberReadDTO
import com.beside.daldal.domain.member.dto.ProfileUpdateDTO
import com.beside.daldal.domain.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/member")
class MemberController(
    private val memberService: MemberService
) {
    @Operation(
        operationId = "readProfile",
        summary = "사용자 정보 조회",
        description = "사용자의 정보를 조회할 수 있습니다.",
        tags = ["profile"]
    )
    @GetMapping("/profile")
    fun readProfile(principal: Principal): ResponseEntity<MemberReadDTO> {
        val email = principal.name
        return ResponseEntity.ok(memberService.findByEmail(email))
    }

    @Operation(
        operationId = "updateProfile",
        summary = "사용자의 프로필을 업데이트할 수 있습니다.",
        description = "이미지를 첨부하지 않는 경우 기존의 이미지가 그대로 사용됩니다.",
        tags = ["profile"]
    )
    @Parameters(
        Parameter(
            name = "dto",
            description = "프로필 업데이트 정보",
            required = true,
            schema = Schema(implementation = ProfileUpdateDTO::class)
        ),
        Parameter(
            name = "file",
            description = "프로필 이미지",
            required = false,
            schema = Schema(type = "file")
        )
    )
    @PutMapping("/profile")
    fun updateProfile(
        principal: Principal,
        @RequestPart("dto") dto: ProfileUpdateDTO,
        @RequestPart("file", required = false) file: MultipartFile?
    ): ResponseEntity<MemberReadDTO> {
        val email = principal.name
        return if (file == null) {
            ResponseEntity.ok(memberService.update(email, dto))
        } else {
            ResponseEntity.ok(memberService.update(email, dto, file))
        }
    }
}