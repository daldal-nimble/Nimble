package com.beside.daldal.domain.review.controller

import com.beside.daldal.domain.review.dto.ReviewsDTO
import com.beside.daldal.domain.review.service.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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

}
