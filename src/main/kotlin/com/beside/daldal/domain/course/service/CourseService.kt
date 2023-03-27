package com.beside.daldal.domain.course.service

import com.beside.daldal.domain.course.dto.CourseCreateDTO
import com.beside.daldal.domain.course.dto.CourseReadDTO
import com.beside.daldal.domain.course.dto.CourseUpdateDTO
import com.beside.daldal.domain.course.error.CourseAuthorizationException
import com.beside.daldal.domain.course.error.CourseNotFoundException
import com.beside.daldal.domain.course.repository.CourseRepository
import com.beside.daldal.domain.member.error.MemberNotFoundException
import com.beside.daldal.domain.member.repository.MemberRepository
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
    fun findMyCourses(email :String):List<CourseReadDTO>{
        val memberId = memberRepository.findByEmail(email)?.id ?: throw MemberNotFoundException()
        val courses = courseRepository.findAllByUserId(memberId)

        // 각각의 코스가 사용자가 뛰었는지 확인 할 수 있어야한다
        

        return courses.map { course-> CourseReadDTO.from(course) }
    }


    @Transactional
    fun delete(courseId: String) {
        courseRepository.deleteById(courseId)
    }

    @Transactional
    fun create(email: String, dto: CourseCreateDTO): CourseReadDTO {
        val memberId = memberRepository.findByEmail(email)?.id ?: throw MemberNotFoundException()
        val course = dto.toEntity(memberId)
        return CourseReadDTO.from(courseRepository.save(course))
    }

    @Transactional
    fun update(email: String, dto: CourseUpdateDTO): CourseReadDTO {
        val memberId = memberRepository.findByEmail(email)?.id ?: throw MemberNotFoundException()
        val course = courseRepository.findById(dto.id).orElseThrow { throw CourseNotFoundException() }

        if (memberId != course.userId) throw CourseAuthorizationException()
        val newCourse = dto.toEntity()
        return CourseReadDTO.from(courseRepository.save(newCourse))
    }
}