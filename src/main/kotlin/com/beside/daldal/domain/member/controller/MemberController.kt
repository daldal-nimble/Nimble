package com.beside.daldal.domain.member.controller

import com.beside.daldal.domain.member.dto.MemberReadDTO
import com.beside.daldal.domain.member.dto.ProfileUpdateDTO
import com.beside.daldal.domain.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@RestController
@RequestMapping("/api/v1/member")
class MemberController(
    private val memberService: MemberService
) {
    @GetMapping("/profile")
    fun readProfile(principal: Principal): ResponseEntity<MemberReadDTO> {
        val email = principal.name
        return ResponseEntity.ok(memberService.findByEmail(email))
    }

    @PutMapping("/profile")
    fun updateProfile(principal: Principal, @RequestBody dto: ProfileUpdateDTO): ResponseEntity<MemberReadDTO> {
        val email = principal.name
        return ResponseEntity.ok(memberService.update(email, dto))
    }

    @PutMapping("/profile/with/image")
    fun updateProfile(
        principal: Principal,
        @RequestPart("dto") dto: ProfileUpdateDTO,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<MemberReadDTO> {
        val email = principal.name
        return ResponseEntity.ok(memberService.update(email, dto, file))
    }
}