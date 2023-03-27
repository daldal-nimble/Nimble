package com.beside.daldal.domain.course.dto

import com.beside.daldal.domain.course.entity.Course

class CourseCreateDTO (
    val name:String,
    val points : List<Map<String,Any?>>
){
    fun toEntity(userId : String) : Course = Course(userId = userId, name = name, points = points)
}