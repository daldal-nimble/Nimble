package com.beside.daldal.domain.course.dto

import java.time.Duration
import java.time.LocalDate

class CourseComplexDTO(
    val id : String,
    val name: String,
    val points : List<Map<String, Any?>>,
    val date : LocalDate,
    val didRun : Boolean,
    val duration: Duration
)