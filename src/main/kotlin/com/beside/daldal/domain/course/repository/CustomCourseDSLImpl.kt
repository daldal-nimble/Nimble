package com.beside.daldal.domain.course.repository

import com.beside.daldal.domain.course.entity.Course
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class CustomCourseDSLImpl(
    private val mongoTemplate: MongoTemplate
) : CustomCourseDSL {
    override fun findPopularCourse(): List<Course> {
        var query = Query()
        val sort = Sort.by(Sort.Direction.DESC, "scrap")
        query = query.with(sort).limit(9)
        return  mongoTemplate.find(query, Course::class.java)
    }
}