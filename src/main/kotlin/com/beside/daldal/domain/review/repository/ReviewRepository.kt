package com.beside.daldal.domain.review.repository

import com.beside.daldal.domain.review.entity.Review
import com.beside.daldal.domain.review.entity.ReviewFeature
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : MongoRepository<Review, String>, CustomReviewDSL {
    fun findAllByMemberId(id: String): List<Review>

    @Query("{ features :  { \$in : ?0 }}")
    fun findAllByFilter(features: List<ReviewFeature>, pageable: Pageable): Page<Review>
}