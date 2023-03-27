package com.beside.daldal.domain.course.dto

import com.beside.daldal.domain.course.entity.Course
import java.time.Duration

class CourseCreateDTO (
    val name:String,
    val memberId :String,
    val distance:Long,
    val duration:Duration,
    val points : List<Map<String,Any?>>
){
    fun toEntity(userId : String) : Course =
        Course(
            name = name,
            memberId = memberId,
            distance = distance,
            duration = duration,
            points = points)
}