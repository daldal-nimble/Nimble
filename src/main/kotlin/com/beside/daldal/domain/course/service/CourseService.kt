package com.beside.daldal.domain.course.service

import com.beside.daldal.domain.course.dto.CourseCreateDTO
import com.beside.daldal.domain.course.dto.CourseReadDTO
import com.beside.daldal.domain.course.dto.CourseUpdateDTO
import com.beside.daldal.domain.course.error.CourseAuthorizationException
import com.beside.daldal.domain.course.error.CourseNotFoundException
import com.beside.daldal.domain.course.repository.CourseRepository
import com.beside.daldal.domain.member.error.MemberNotFoundException
import com.beside.daldal.domain.member.repository.MemberRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val memberRepository: MemberRepository,
    private val redisTemplate: RedisTemplate<String, Set<CourseReadDTO>>
) {
    @Transactional(readOnly = true)
    fun findById(courseId: String): CourseReadDTO {
        val course = courseRepository.findById(courseId).orElseThrow { throw CourseNotFoundException() }
        return CourseReadDTO.from(course)
    }

    @Transactional(readOnly = true)
    fun findMyCourses(email: String): List<CourseReadDTO> {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val isRunCourse = courseRepository.findAllByIds(member.isRun)
        val isNotRunCourse = courseRepository.findAllByIds(member.isNotRun)
        val result: MutableList<CourseReadDTO> = mutableListOf()
        for (course in isNotRunCourse + isRunCourse) {
            result.add(CourseReadDTO.from(course))
        }
        return result
    }

    @Transactional
    fun delete(email: String, courseId: String) {
        val memberId = memberRepository.findByEmail(email)?.id ?: throw MemberNotFoundException()
        val course = courseRepository.findById(courseId).orElseThrow { throw CourseNotFoundException() }
        if (course.memberId == memberId)
            throw CourseAuthorizationException()
        courseRepository.deleteById(courseId)
        // 해당 부분은 리뷰의 데이터와도 연관되어 있기 때문에 어떻게 삭제할지 고민해야함.
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
    fun update(email: String, courseId: String, dto: CourseUpdateDTO): CourseReadDTO {
        val memberId = memberRepository.findByEmail(email)?.id ?: throw MemberNotFoundException()
        val course = courseRepository.findById(courseId).orElseThrow { throw CourseNotFoundException() }

        if (memberId != course.memberId)
            throw CourseAuthorizationException()

        val newCourse = dto.toEntity(courseId, memberId)
        return CourseReadDTO.from(courseRepository.save(newCourse))
    }

    @Transactional(readOnly = true)
    fun findPopularCourse(): CourseReadDTO {
        var dtoSet = redisTemplate.opsForValue().get("popularCourse")
        if (dtoSet != null && dtoSet.isNotEmpty()) {
            return dtoSet.random()
        }
        val courses = courseRepository.findPopularCourse()
        dtoSet = courses.map { CourseReadDTO.from(it) }.toSet()
        redisTemplate.opsForValue().set("popularCourse", dtoSet)
        return CourseReadDTO.from(courses.random())
    }
}