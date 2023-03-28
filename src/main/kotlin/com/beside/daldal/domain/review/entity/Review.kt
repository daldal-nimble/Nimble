package com.beside.daldal.domain.review.entity

import com.beside.daldal.domain.comment.entity.Comment
import com.beside.daldal.shared.entity.BaseTimeEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime

@Document("review")
class Review(
    @Id
    var id: String? = null,
    var memberId: String,
    var courseId: String,
    var content: String,
    var favorite : Long,
    var imageUrl: String,
    var sentiment:ReviewSentiment = ReviewSentiment.MID,
    var features: List<ReviewFeature>,
    var comments : List<Comment>
): BaseTimeEntity(){
    fun delete(){
        deletedAt = LocalDateTime.now()
    }
}