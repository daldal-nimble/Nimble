package com.beside.daldal.domain.course.dto

import com.beside.daldal.domain.course.entity.Course
import com.beside.daldal.domain.course.error.CourseNotFoundException
import java.time.Duration

class CourseReadDTO (
    val id :String,
    val name :String,
    var distance : Long,// m 단위
    var duration: Long,
    var points : List<Map<String, Any?>>,
){
    companion object{
        fun from(entity : Course): CourseReadDTO {
            return CourseReadDTO(
                id = entity.id ?:  throw CourseNotFoundException(),
                name = entity.name,
                distance = entity.distance,
                duration = entity.duration,
                points = entity.points,
            )
        }
    }
}