package com.beside.daldal.domain.course.dto

import com.beside.daldal.domain.course.entity.Course


class CourseUpdateDTO(
    val id: String,
    val name: String,
    val userId: String,
    val points: List<Map<String, Any?>>
){
    fun toEntity():Course = Course(id, userId, name, points)
}