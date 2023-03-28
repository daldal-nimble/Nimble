package com.beside.daldal.domain.review.entity

import com.beside.daldal.domain.comment.entity.Comment
import com.beside.daldal.domain.review.dto.ReviewUpdateDTO
import com.beside.daldal.shared.entity.BaseTimeEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("review")
class Review(
    @Id
    var id: String? = null,
    var memberId: String,
    var courseId: String,
    var content: String,
    var favorite: Long = 0,
    var imageUrl: String,
    var sentiment: ReviewSentiment = ReviewSentiment.NEUTRAL,
    var features: List<ReviewFeature>,
    var comments: List<Comment> = listOf(),
) : BaseTimeEntity() {
    fun delete() {
        deletedAt = LocalDateTime.now()
    }

    fun update(dto: ReviewUpdateDTO) {
        content = dto.content
        imageUrl = ""
        features = dto.features.map { ReviewFeature.valueOf(it) }
    }
}