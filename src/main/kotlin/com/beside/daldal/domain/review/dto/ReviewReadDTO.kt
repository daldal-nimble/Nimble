package com.beside.daldal.domain.review.dto

import com.beside.daldal.domain.comment.dto.CommentDTO
import com.beside.daldal.domain.course.entity.Course
import com.beside.daldal.domain.member.entity.Member
import com.beside.daldal.domain.review.entity.Review
import com.beside.daldal.domain.review.entity.ReviewSentiment
import com.beside.daldal.domain.review.error.ReviewNotFoundException

class ReviewReadDTO(
    val id: String,
    val memberId: String,
    val courseId: String,
    val content: String,
    val imageUrl: String,
    val favourite: Long,
    val bookmark: Long,
    val sentiment: ReviewSentiment,
    val isFavorite: Boolean,
    val isBookmarked: Boolean,
    val features: List<String>,
    val comments: List<CommentDTO>
) {
    companion object {
        fun from(review: Review, course: Course, member: Member): ReviewReadDTO {
            return ReviewReadDTO(
                id = review.id ?: throw ReviewNotFoundException(),
                memberId = review.memberId,
                courseId = review.courseId,
                content = review.content,
                favourite = review.favorite,
                sentiment = review.sentiment,
                features = review.features.map { it.name },
                bookmark = course.bookmark,
                imageUrl = review.imageUrl,
                isFavorite = review.id in member.favorite,
                isBookmarked = course.id in member.bookmarked,
                comments = review.comments.map { CommentDTO.from(it) }
            )
        }
    }
}
