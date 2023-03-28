package com.beside.daldal.domain.review.controller

import com.beside.daldal.domain.review.dto.ReviewCreateDTO
import com.beside.daldal.domain.review.dto.ReviewDTO
import com.beside.daldal.domain.review.dto.ReviewReadDTO
import com.beside.daldal.domain.review.dto.ReviewsDTO
import com.beside.daldal.domain.review.service.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/reviews")
class ReviewController(
    private val reviewService: ReviewService
) {
    @GetMapping("")
    fun findMyReview(principal: Principal): ResponseEntity<ReviewsDTO> {
        return ResponseEntity.ok(reviewService.findMyReview(principal.name))
    }

    @GetMapping("/{reviewId}")
    fun findReviewById(@PathVariable reviewId: String): ResponseEntity<ReviewReadDTO> {
        return ResponseEntity.ok(reviewService.findById(reviewId))
    }

    @PostMapping("/{courseId}")
    fun createReview(
        principal: Principal,
        @PathVariable courseId: String,
        @RequestBody dto: ReviewCreateDTO
    ): ResponseEntity<ReviewDTO> {
        return ResponseEntity.ok(reviewService.createReview(principal.name, courseId, dto))
    }

    @DeleteMapping("/{reviewId}")
    fun deleteReview(@PathVariable reviewId: String): ResponseEntity<String> {
        reviewService.deleteReview(reviewId)
        return ResponseEntity.ok(reviewId)
    }

}
