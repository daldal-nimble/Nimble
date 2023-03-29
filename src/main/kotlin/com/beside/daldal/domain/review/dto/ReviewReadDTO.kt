package com.beside.daldal.domain.review.dto

import com.beside.daldal.domain.comment.dto.CommentDTO
import com.beside.daldal.domain.review.entity.Review
import com.beside.daldal.domain.review.entity.ReviewSentiment
import com.beside.daldal.domain.review.error.ReviewNotFoundException

class ReviewReadDTO(
    val id: String,
    val memberId: String,
    val courseId: String,
    val content: String,
    val imageUrl: String,
    val favorite: Long,
    val bookmark: Long,
    val sentiment: ReviewSentiment,
    val features: List<String>,
    val comments: List<CommentDTO>
) {
    companion object {
        fun from(review: Review): ReviewReadDTO {
            return ReviewReadDTO(
                id = review.id ?: throw ReviewNotFoundException(),
                memberId = review.memberId,
                courseId = review.courseId,
                content = review.content,
                favorite = review.favorite,
                sentiment = review.sentiment,
                features = review.features.map { it.name },
                bookmark = review.bookmark,
                imageUrl = review.imageUrl,
                comments = review.comments.map { CommentDTO.of(it) }
            )
        }
    }
}
