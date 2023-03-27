package com.beside.daldal.domain.course.controller

import com.beside.daldal.domain.course.dto.CourseComplexDTO
import com.beside.daldal.domain.course.dto.CourseCreateDTO
import com.beside.daldal.domain.course.dto.CourseReadDTO
import com.beside.daldal.domain.course.dto.CourseUpdateDTO
import com.beside.daldal.domain.course.service.CourseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/course")
class CourseController(
    private val courseService: CourseService
) {

    @GetMapping("/{courseId}")
    fun findById(@PathVariable courseId: String) : ResponseEntity<CourseReadDTO> =
        ResponseEntity.ok(courseService.findById(courseId))

    @GetMapping("")
    fun findMyCourses(principal: Principal):ResponseEntity<List<CourseComplexDTO>>{
        val email = principal.name
        return ResponseEntity.ok(courseService.findMyCourses(email))
    }

    @PostMapping("")
    fun create(
        @RequestBody dto: CourseCreateDTO, principal: Principal
    ): ResponseEntity<CourseReadDTO> {
        val email: String = principal.name
        return ResponseEntity.ok(courseService.create(email, dto))
    }

    @DeleteMapping("/{courseId}")
    fun delete(@PathVariable("courseId") courseId: String
    ) : ResponseEntity<String> {
        courseService.delete(courseId)
        return ResponseEntity.ok(courseId)
    }

    @PutMapping("")
    fun update(@RequestBody dto : CourseUpdateDTO, principal: Principal) : ResponseEntity<CourseReadDTO> {
        val email = principal.name
        return ResponseEntity.ok(courseService.update(email, dto))
    }

    @GetMapping("/popular")
    fun popular() : ResponseEntity<List<CourseReadDTO>>{
        return ResponseEntity.ok().body(courseService.popular())
    }
}