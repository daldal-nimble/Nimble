package com.beside.daldal.domain.auth.service

import com.beside.daldal.domain.member.entity.Member
import com.beside.daldal.domain.member.error.MemberNotFoundException
import com.beside.daldal.domain.member.repository.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CustomUserDetailService(
    private val memberRepository: MemberRepository
) : UserDetailsService {
    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val member: Member = memberRepository.findByEmail(username) ?: throw MemberNotFoundException()
        return createUserDetails(member)
    }

    private fun createUserDetails(member: Member): UserDetails {
        val grantedAuthority = SimpleGrantedAuthority(member.authrity.toString())
        return User(member.email, "", Collections.singleton(grantedAuthority))
    }
}