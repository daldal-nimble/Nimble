package com.beside.daldal.domain.course.repository

import com.beside.daldal.domain.course.entity.Course
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository  : MongoRepository<Course, String>, CustomCourseDSL{
    @Query("{ _id : { \$in : ?0 } }")
    fun findAllByIds(ids: List<String>) : List<Course>
}