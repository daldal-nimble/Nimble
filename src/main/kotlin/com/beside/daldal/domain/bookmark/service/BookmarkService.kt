package com.beside.daldal.domain.bookmark.service

import com.beside.daldal.domain.course.entity.Course
import com.beside.daldal.domain.course.error.CourseNotFoundException
import com.beside.daldal.domain.course.repository.CourseRepository
import com.beside.daldal.domain.member.error.MemberNotFoundException
import com.beside.daldal.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class BookmarkService(
    private val memberRepository: MemberRepository,
    private val courseRepository: CourseRepository
) {

    fun bookmarkUp(email: String, courseId: String): String {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val course: Course = courseRepository.findById(courseId).orElseThrow { throw CourseNotFoundException() }

        if(courseId in member.bookmarked) return courseId

        member.bookmarkUp(courseId)
        course.bookmarkUp()

        memberRepository.save(member)
        courseRepository.save(course)
        return courseId
    }

    fun bookmarkDown(email: String, courseId: String): String {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val course: Course = courseRepository.findById(courseId).orElseThrow { throw CourseNotFoundException() }

        if(courseId !in member.bookmarked) return courseId
        member.bookmarkDown(courseId)
        course.bookmarkDown()

        memberRepository.save(member)
        courseRepository.save(course)
        return courseId
    }
}