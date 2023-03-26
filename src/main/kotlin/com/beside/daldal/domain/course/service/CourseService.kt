package com.beside.daldal.domain.course.service

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

@Service
class CourseService(private val mongo : MongoTemplate) {
    fun insert(map : Map<String, Any?>){
        mongo.insert(map, "course")
    }
}