package com.beside.daldal.domain.review.repository

import com.beside.daldal.domain.review.entity.Review

interface CustomReviewDSL {
    fun findPopularReview():List<Review>
}