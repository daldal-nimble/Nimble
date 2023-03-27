package com.beside.daldal.domain.course.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Course(
    @Id
    var id : String? = null,
    var userId : String,
    var name : String,
    var points : List<Map<String, Any?>>,
){
}