package com.beside.daldal.domain.course.dto

import com.beside.daldal.domain.course.entity.Course
import com.beside.daldal.domain.course.error.CourseNotFoundException

class CourseComplexDTO(
    val id: String,
    val name: String,
    val distance: Long,
    val duration: Long,
    val points: List<Map<String, Any?>>,
    val didRun: Boolean
    ) {
    companion object {
        fun from(entity: Course, didRun: Boolean): CourseComplexDTO {
            return CourseComplexDTO(
                id = entity.id ?: throw CourseNotFoundException(),
                name = entity.name,
                points = entity.points,
                distance = entity.distance,
                duration = entity.duration,
                didRun = didRun
            )
        }
    }
}