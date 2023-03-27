package com.beside.daldal.domain.course.repository

import com.beside.daldal.domain.course.entity.Course
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository  : MongoRepository<Course, String>{
    fun findAllByUserId(userId : String) : List<Course>
}