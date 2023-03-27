package com.beside.daldal.domain.course.dto

import com.beside.daldal.domain.course.entity.Course
import com.beside.daldal.domain.course.error.CourseNotFoundException
import java.time.Duration
import java.time.LocalDate

class CourseComplexDTO(
    val id: String,
    val name: String,
    val points: List<Map<String, Any?>>,
    val didRun: Boolean,
    val duration: Duration
) {
    companion object {
        fun from(entity: Course, didRun: Boolean): CourseComplexDTO {
            return CourseComplexDTO(
                id = entity.id ?: throw CourseNotFoundException(),
                name = entity.name,
                points = entity.points,
                didRun = didRun,
                duration = entity.duration
            )
        }
    }
}