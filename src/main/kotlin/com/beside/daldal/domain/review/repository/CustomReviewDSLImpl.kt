package com.beside.daldal.domain.review.repository

import com.beside.daldal.domain.review.entity.Review
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class CustomReviewDSLImpl(
    private val mongoTemplate: MongoTemplate
) : CustomReviewDSL {
    override fun findPopularReview(): List<Review> {
        val sort = Sort.by(Sort.Direction.DESC, "favorite")
        val query = Query().with(sort).limit(9)
        return mongoTemplate.find(query, Review::class.java)
    }
}