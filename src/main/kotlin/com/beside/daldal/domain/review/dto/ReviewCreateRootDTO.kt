package com.beside.daldal.domain.review.dto

import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.multipart.MultipartFile

class ReviewCreateRootDTO(
    val dto: ReviewCreateDTO,
    val file: MultipartFile
)