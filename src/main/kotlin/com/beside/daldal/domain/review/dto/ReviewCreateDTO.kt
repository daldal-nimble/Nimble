package com.beside.daldal.domain.review.dto

import com.beside.daldal.domain.review.entity.Review
import com.beside.daldal.domain.review.entity.ReviewFeature

class ReviewCreateDTO(
    val content: String,
    val features: List<String>
) {
    fun toEntity(memberId: String, courseId: String): Review {
        return Review(
            memberId = memberId,
            courseId = courseId,
            content = content,
            imageUrl = "",
            // List<String> -> List<ReviewFeature>, ReviewFeature Enum
            features = features.map { ReviewFeature.valueOf(it) },
        )
    }
}
