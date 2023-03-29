package com.beside.daldal.domain.review.repository

import com.beside.daldal.domain.review.entity.Review
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository :MongoRepository<Review, String>, CustomReviewDSL {
    fun findAllByMemberId(id: String) : List<Review>
}