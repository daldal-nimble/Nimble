package com.beside.daldal.domain.review.entity

import com.beside.daldal.domain.comment.entity.Comment
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
    var bookmark: Long = 0,
    var imageUrl: String,
    var sentiment: ReviewSentiment = ReviewSentiment.NEUTRAL,
    var features: List<ReviewFeature>,
    var comments: List<Comment> = listOf(),
) : BaseTimeEntity() {
    fun delete() {
        deletedAt = LocalDateTime.now()
    }

    fun update(content: String, features: List<ReviewFeature>, imageUrl: String, sentiment: ReviewSentiment) {
        this.content = content
        this.features = features
        this.imageUrl = imageUrl
        this.sentiment = sentiment
    }

    override fun toString(): String {
        return "Review(id=$id, memberId='$memberId', courseId='$courseId', content='$content', favorite=$favorite, imageUrl='$imageUrl', sentiment=$sentiment, features=$features, comments=$comments)"
    }

    fun bookmarkUp() {
        synchronized(this) {
            bookmark += 1
        }
    }

    fun bookmarkDown() {
        synchronized(this) {
            bookmark -= 1
        }
    }
}