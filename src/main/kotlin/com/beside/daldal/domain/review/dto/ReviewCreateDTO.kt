package com.beside.daldal.domain.review.dto

import com.beside.daldal.domain.review.entity.Review
import com.beside.daldal.domain.review.entity.ReviewFeature
import com.beside.daldal.domain.review.entity.ReviewSentiment

class ReviewCreateDTO(
    var content: String,
    var features: List<ReviewFeature>
) {

    fun toEntity(memberId: String, courseId: String, imageUrl: String, sentiment: ReviewSentiment): Review {
        return Review(
            memberId = memberId,
            courseId = courseId,
            content = content ?: throw IllegalArgumentException(),
            imageUrl = imageUrl,
            sentiment = sentiment,
            // List<String> -> List<ReviewFeature>, ReviewFeature Enum
            features = features ?: throw IllegalArgumentException(),
        )
    }

    override fun toString(): String {
        return "ReviewCreateDTO(content=$content, features=$features)"
    }
}
