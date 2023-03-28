package com.beside.daldal.domain.course.dto

import com.beside.daldal.domain.course.entity.Course


class CourseUpdateDTO(
    val name: String,
    val memberId: String,
    val distance: Long,
    val duration: Long,
    val points: List<Map<String, Any?>>
) {
    fun toEntity(courseId: String): Course = Course(courseId, name, memberId, distance, duration, points)
}