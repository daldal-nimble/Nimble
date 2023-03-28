package com.beside.daldal.domain.course.dto

import com.beside.daldal.domain.course.entity.Course
import java.time.Duration

class CourseCreateDTO (
    val name:String,
    val distance:Long,
    val duration:Long,
    val points : List<Map<String,Any?>>
){
    fun toEntity(memberId : String) : Course =
        Course(
            name = name,
            memberId = memberId,
            distance = distance,
            duration = duration,
            points = points)
}