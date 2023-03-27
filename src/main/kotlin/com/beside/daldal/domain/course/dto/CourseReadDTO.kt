package com.beside.daldal.domain.course.dto

import com.beside.daldal.domain.course.entity.Course
import com.beside.daldal.domain.course.error.CourseNotFoundException

class CourseReadDTO (
    val id :String,
    val name :String,
    val points : List<Map<String, Any?>>
){
    companion object{
        fun from(entity : Course): CourseReadDTO {
            return CourseReadDTO(
                id = entity.id ?:  throw CourseNotFoundException(),
                name = entity.name,
                points = entity.points
            )
        }
    }
}