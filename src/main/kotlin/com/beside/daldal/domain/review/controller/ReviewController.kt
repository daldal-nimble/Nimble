package com.beside.daldal.domain.review.controller

import com.beside.daldal.domain.review.dto.*
import com.beside.daldal.domain.review.service.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@RestController
@RequestMapping("/api/v1/review")
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

    @PostMapping("/{courseId}", consumes = ["multipart/form-data"])
    fun createReview(
        principal: Principal,
        @PathVariable courseId: String,
        @RequestPart("dto") dto: ReviewCreateDTO,
        @RequestPart("file") file : MultipartFile
    ): ResponseEntity<ReviewDTO> {
        return ResponseEntity.ok(reviewService.createReview(principal.name, courseId, dto, file))
    }

    @DeleteMapping("/{reviewId}")
    fun deleteReview(@PathVariable reviewId: String): ResponseEntity<String> {
        reviewService.deleteReview(reviewId)
        return ResponseEntity.ok(reviewId)
    }

    @PutMapping("/{reviewId}")
    fun updateReview(
        principal: Principal,
        @PathVariable reviewId: String,
        @RequestBody dto: ReviewUpdateDTO
    ): ResponseEntity<ReviewDTO> {
        val email = principal.name
        return ResponseEntity.ok(reviewService.updateReview(email, reviewId, dto))
    }
}
