package com.beside.daldal.domain.review.dto

import com.beside.daldal.domain.comment.dto.CommentDTO
import com.beside.daldal.domain.course.dto.CourseReadDTO
import com.beside.daldal.domain.course.entity.Course
import com.beside.daldal.domain.member.dto.MemberReadDTO
import com.beside.daldal.domain.member.entity.Member
import com.beside.daldal.domain.review.entity.Review
import com.beside.daldal.domain.review.entity.ReviewFeature
import com.beside.daldal.domain.review.entity.ReviewSentiment
import com.beside.daldal.domain.review.error.ReviewNotFoundException
import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class ReviewReadDTO(
    val id: String,
    val member: MemberReadDTO,
    val course : CourseReadDTO,
    val content: String,
    val imageUrl: String,
    val favourite: Long,
    val sentiment: ReviewSentiment,
    val isFavorite: Boolean,
    val isBookmarked: Boolean,
    val features: List<ReviewFeature>,
    val comments: List<CommentDTO>,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape =JsonFormat.Shape.STRING, timezone = "Asia/Seoul")
    val createAt : LocalDateTime?
) {
    companion object {
        fun from(review: Review, course: Course, me: Member, creator : Member): ReviewReadDTO {
            return ReviewReadDTO(
                id = review.id ?: throw ReviewNotFoundException(),
                member = MemberReadDTO.from(creator),
                course = CourseReadDTO.from(course),
                content = review.content,
                favourite = review.favorite,
                sentiment = review.sentiment,
                features = review.features,
                imageUrl = review.imageUrl,
                isFavorite = review.id in me.favorite,
                isBookmarked = course.id in me.bookmarked,
                comments = review.comments.map { CommentDTO.from(it) },
                createAt = review.createdAt
            )
        }
    }
}
