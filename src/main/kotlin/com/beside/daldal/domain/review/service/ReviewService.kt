package com.beside.daldal.domain.review.service

import com.beside.daldal.domain.member.error.MemberNotFoundException
import com.beside.daldal.domain.member.repository.MemberRepository
import com.beside.daldal.domain.review.dto.ReviewCreateDTO
import com.beside.daldal.domain.review.dto.ReviewDTO
import com.beside.daldal.domain.review.dto.ReviewReadDTO
import com.beside.daldal.domain.review.dto.ReviewsDTO
import com.beside.daldal.domain.review.repository.ReviewRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewService(
    private val memberRepository: MemberRepository,
    private val reviewRepository: ReviewRepository
) {
    @Transactional(readOnly = true)
    fun findMyReview(email: String): ReviewsDTO {
        val memberId = memberRepository.findByEmail(email)?.id
            ?: throw MemberNotFoundException()
        val reviews = reviewRepository.findAllByMemberId(memberId)
        val result = mutableListOf<ReviewDTO>()
        for (review in reviews)
            result.add(ReviewDTO.from(review))
        return ReviewsDTO(result)
    }

    @Transactional(readOnly = true)
    fun findById(reviewId: String): ReviewReadDTO {
        val review = reviewRepository.findById(reviewId).orElseThrow { throw MemberNotFoundException() }
        return ReviewReadDTO.from(review)
    }

    @Transactional
    fun createReview(email: String, courseId: String, dto: ReviewCreateDTO): ReviewDTO {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val memberId = member.id ?: throw MemberNotFoundException()
        val review = dto.toEntity(memberId, courseId)
        reviewRepository.save(review)
        return ReviewDTO.from(review)
    }

    @Transactional
    fun deleteReview(reviewId: String) {
        reviewRepository.deleteById(reviewId)
    }

//    @Transactional
//    fun

}