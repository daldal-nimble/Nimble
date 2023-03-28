package com.beside.daldal.domain.review.dto

import com.beside.daldal.domain.review.entity.Review
import com.beside.daldal.domain.review.entity.ReviewFeature
import com.beside.daldal.domain.review.entity.ReviewSentiment
import com.beside.daldal.domain.review.error.ReviewNotFoundException

class ReviewDTO(
    val id: String,
    val memberId: String,
    val courseId: String,
    val content: String,
    val favorite: Long,
    val imageUrl: String,
    val sentiment: ReviewSentiment,
    val features: List<ReviewFeature>,
    ) {
    companion object{
        fun from(review: Review): ReviewDTO {
            return ReviewDTO(
                id = review.id ?: throw ReviewNotFoundException(),
                memberId = review.memberId,
                courseId = review.courseId,
                content = review.content,
                favorite = review.favorite,
                imageUrl = review.imageUrl,
                sentiment = review.sentiment,
                features = review.features
            )
        }
    }
}