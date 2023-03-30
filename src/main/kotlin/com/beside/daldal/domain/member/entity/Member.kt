package com.beside.daldal.domain.member.entity

import com.beside.daldal.shared.entity.BaseTimeEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("member")
class Member(
    @Id
    var id: String? = null,
    var email: String,
    var loginType: LoginType,
    var authrity: Authority = Authority.ROLE_USER,

    var isRun: MutableList<String> = mutableListOf(),
    var isNotRun: MutableList<String> = mutableListOf(),
    var bookmarked: MutableSet<String> = mutableSetOf(),
    var favorite: MutableList<String> = mutableListOf(),

    var profileImageUrl: String,
    var username: String? = null,
    var nickname: String? = null,
    var gender: Gender = Gender.UNKNOWN,
) : BaseTimeEntity() {
    enum class Authority {
        ROLE_USER, ROLE_ADMIN
    }

    fun delete() {
        deletedAt = LocalDateTime.now()
    }

    fun getCourseIds(): List<String> = isRun + isNotRun

    fun favorite(reviewId: String) {
        favorite.add(reviewId)
    }

    fun unfavorite(reviewId: String) {
        favorite.remove(reviewId)
    }

    fun bookmarkUp(courseId: String) {
        bookmarked.add(courseId)
        isNotRun.add(courseId)
    }

    fun bookmarkDown(courseId: String) {
        bookmarked.remove(courseId)
        isNotRun.remove(courseId)
    }

    fun update(nickname: String?, username: String?, gender: Gender, imageUrl: String = this.profileImageUrl) {
        this.nickname = nickname
        this.username = username
        this.gender = gender
        this.profileImageUrl = imageUrl
    }
}