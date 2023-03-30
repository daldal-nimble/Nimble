package com.beside.daldal.domain.member.service

import com.beside.daldal.domain.image.error.ImageNotFoundException
import com.beside.daldal.domain.image.service.ImageService
import com.beside.daldal.domain.member.dto.MemberCreateDTO
import com.beside.daldal.domain.member.dto.MemberReadDTO
import com.beside.daldal.domain.member.dto.ProfileUpdateDTO
import com.beside.daldal.domain.member.entity.Member
import com.beside.daldal.domain.member.error.MemberNotFoundException
import com.beside.daldal.domain.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val imageService: ImageService
) {
    @Value("\${default.profile.default.image}")
    private lateinit var defaultImageUrl: String

    @Transactional
    fun create(dto: MemberCreateDTO): Member = memberRepository.save(dto.toEntity(defaultImageUrl))

    @Transactional
    fun deleteByEmail(email: String) {
        val member: Member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        member.delete()
        memberRepository.save(member)
    }

    @Transactional(readOnly = true)
    fun findByEmail(email: String): MemberReadDTO {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        return MemberReadDTO.from(member)
    }

    @Transactional
    fun update(email: String, dto: ProfileUpdateDTO): MemberReadDTO {
        val member: Member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        member.update(dto.nickname, dto.username, dto.gender)
        return MemberReadDTO.from(memberRepository.save(member))
    }

    @Transactional
    fun update(email: String, dto: ProfileUpdateDTO, file: MultipartFile): MemberReadDTO {
        val member: Member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()

        val words = file.originalFilename?.split(".") ?: throw ImageNotFoundException()
        val key = UUID.randomUUID().toString() + "." + words[words.lastIndex]

        val imageUrl = if (member.profileImageUrl == defaultImageUrl) {
            imageService.upload(file, key, "profile")
        } else {
            imageService.delete(member.profileImageUrl, "profile")
            imageService.upload(file, key, "profile")
        }

        member.update(dto.nickname, dto.username, dto.gender, imageUrl)
        return MemberReadDTO.from(memberRepository.save(member))
    }
}