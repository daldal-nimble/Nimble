package com.beside.daldal.domain.course.service

import com.beside.daldal.domain.course.dto.CourseComplexDTO
import com.beside.daldal.domain.course.dto.CourseCreateDTO
import com.beside.daldal.domain.course.dto.CourseReadDTO
import com.beside.daldal.domain.course.dto.CourseUpdateDTO
import com.beside.daldal.domain.course.error.CourseAuthorizationException
import com.beside.daldal.domain.course.error.CourseNotFoundException
import com.beside.daldal.domain.course.repository.CourseRepository
import com.beside.daldal.domain.member.error.MemberNotFoundException
import com.beside.daldal.domain.member.repository.MemberRepository
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional(readOnly = true)
    fun findById(courseId: String): CourseReadDTO {
        val course = courseRepository.findById(courseId).orElseThrow { throw CourseNotFoundException() }
        return CourseReadDTO.from(course)
    }

    @Transactional(readOnly = true)
    fun findMyCourses(email: String): List<CourseComplexDTO> {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val isRunCourse = courseRepository.findAllByIds(member.isRun)
        val isNotRunCourse = courseRepository.findAllByIds(member.isNotRun)
        // 각각의 코스가 사용자가 뛰었는지 확인 할 수 있어야한다

        val result: MutableList<CourseComplexDTO> = mutableListOf()

        for (course in isRunCourse)
            result.add(CourseComplexDTO.from(course, true))
        for (course in isNotRunCourse)
            result.add(CourseComplexDTO.from(course, false))

        return result
    }

    @Transactional
    fun delete(courseId: String) {
        courseRepository.deleteById(courseId)
    }

    @Transactional
    fun create(email: String, dto: CourseCreateDTO): CourseReadDTO {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val memberId = member.id ?: throw MemberNotFoundException()
        var course = dto.toEntity(memberId)

        course = courseRepository.save(course)
        val courseId = course.id ?: throw CourseNotFoundException()
        member.isNotRun.add(courseId)
        memberRepository.save(member)

        return CourseReadDTO.from(course)
    }

    @Transactional
    fun update(email: String, dto: CourseUpdateDTO): CourseReadDTO {
        val memberId = memberRepository.findByEmail(email)?.id ?: throw MemberNotFoundException()
        val course = courseRepository.findById(dto.id).orElseThrow { throw CourseNotFoundException() }

        if (memberId != course.memberId)
            throw CourseAuthorizationException()

        val newCourse = dto.toEntity()
        return CourseReadDTO.from(courseRepository.save(newCourse))
    }

    @Transactional
    fun popular(): List<CourseReadDTO> = courseRepository.findPopularCourse()
        .map { course -> CourseReadDTO.from(course) }
        .sortedByDescending { dto -> dto.scarp }

}