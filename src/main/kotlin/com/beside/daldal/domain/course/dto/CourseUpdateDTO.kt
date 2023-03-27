package com.beside.daldal.domain.course.dto

import com.beside.daldal.domain.course.entity.Course
import org.springframework.data.geo.Distance
import java.time.Duration


class CourseUpdateDTO(
    val id: String,
    val name: String,
    val memberId: String,
    val distance: Long,
    val duration: Duration,
    val points: List<Map<String, Any?>>
) {
    fun toEntity(): Course = Course(id, name, memberId, distance, duration, points)
}