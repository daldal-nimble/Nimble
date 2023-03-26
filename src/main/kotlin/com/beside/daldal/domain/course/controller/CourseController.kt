package com.beside.daldal.domain.course.controller

import com.beside.daldal.domain.course.service.CourseService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/course")
class CourseController(private val courseService: CourseService){
    @PostMapping("/test")
    fun test(@RequestBody data:String): ResponseEntity<String> {
        println(data)
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(data)
    }
}