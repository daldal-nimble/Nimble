package com.beside.daldal.domain.review.dto

import com.beside.daldal.domain.review.entity.Review
import com.beside.daldal.domain.review.entity.ReviewFeature
import kotlin.contracts.contract

class ReviewUpdateDTO(
    val content: String,
    val features: List<ReviewFeature>
) {
    fun toEntity(entity: Review, imageUrl: String): Review {
        return Review(
            id = entity.id,
            content = content,
            features = features,
            courseId = entity.courseId,
            memberId = entity.memberId,
            favorite = entity.favorite,
            imageUrl = imageUrl,
            comments = entity.comments
        )
    }
}
