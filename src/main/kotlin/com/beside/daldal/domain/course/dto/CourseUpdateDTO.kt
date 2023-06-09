package com.beside.daldal.domain.course.dto

import com.beside.daldal.domain.course.entity.Course


class CourseUpdateDTO(
    val name: String,
    val distance: Long,
    val duration: Long,
    val points: List<Map<String, Any?>>
) {
    fun toEntity(courseId: String, memberId: String): Course = Course(
        id = courseId,
        memberId = memberId,
        name = name,
        distance = distance,
        duration = duration,
        points = points
    )
}