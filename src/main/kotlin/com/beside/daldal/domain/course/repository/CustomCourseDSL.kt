package com.beside.daldal.domain.course.repository

import com.beside.daldal.domain.course.entity.Course

interface CustomCourseDSL {
    fun findPopularCourse() : List<Course>
}