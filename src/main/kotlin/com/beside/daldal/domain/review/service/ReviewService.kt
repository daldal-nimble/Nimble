package com.beside.daldal.domain.review.service

import com.beside.daldal.domain.image.error.ImageNotFoundException
import com.beside.daldal.domain.image.service.ImageService
import com.beside.daldal.domain.member.error.MemberNotFoundException
import com.beside.daldal.domain.member.repository.MemberRepository
import com.beside.daldal.domain.review.dto.*
import com.beside.daldal.domain.review.entity.Review
import com.beside.daldal.domain.review.entity.ReviewSentiment
import com.beside.daldal.domain.review.error.ReviewAuthorizationException
import com.beside.daldal.domain.review.error.ReviewNotFoundException
import com.beside.daldal.domain.review.repository.ReviewRepository
import com.beside.daldal.domain.sentiment.dto.Document
import com.beside.daldal.domain.sentiment.service.SentimentService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class ReviewService(
    private val memberRepository: MemberRepository,
    private val reviewRepository: ReviewRepository,
    private val imageService: ImageService,
    private val sentimentService: SentimentService
) {
    @Transactional(readOnly = true)
    fun findMyReview(email: String): MutableList<ReviewDTO> {
        val memberId = memberRepository.findByEmail(email)?.id
            ?: throw MemberNotFoundException()
        val reviews = reviewRepository.findAllByMemberId(memberId)
        val result = mutableListOf<ReviewDTO>()
        for (review in reviews)
            result.add(ReviewDTO.from(review))
        return result
    }

    @Transactional(readOnly = true)
    fun findById(reviewId: String): ReviewReadDTO {
        val review = reviewRepository.findById(reviewId).orElseThrow { throw ReviewNotFoundException() }
        return ReviewReadDTO.from(review)
    }

    @Transactional
    fun createReview(
        email: String,
        courseId: String,
        dto: ReviewCreateDTO,
        file: MultipartFile
    ): ReviewDTO {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val memberId = member.id ?: throw MemberNotFoundException()

        // 파일 이름 정하기
        val words = file.originalFilename?.split(".") ?: throw ImageNotFoundException()
        val key = UUID.randomUUID().toString() + "." + words[words.lastIndex]
        // image upload
        println(key)
        val imageUrl = imageService.upload(file, key, "review")

        // sentiment 분석
        val document = sentimentService.analyze(dto.content)

        val review = dto.toEntity(
            memberId,
            courseId,
            imageUrl,
            ReviewSentiment.valueOf(document.sentiment.uppercase())
        )
        reviewRepository.save(review)
        return ReviewDTO.from(review)
    }

    @Transactional
    fun deleteReview(reviewId: String) {
        val review = reviewRepository.findById(reviewId).orElseThrow { throw ReviewNotFoundException() }
        val words = review.imageUrl.split("/")
        imageService.delete(words[words.lastIndex], "review")
        reviewRepository.deleteById(reviewId)
    }

    @Transactional
    fun updateReview(
        email: String,
        reviewId: String,
        dto: ReviewUpdateDTO,
        file: MultipartFile
    ): ReviewDTO {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val memberId = member.id ?: throw MemberNotFoundException()
        val review: Review = reviewRepository.findById(reviewId).orElseThrow { throw ReviewNotFoundException() }
        if (review.memberId != memberId)
            throw ReviewAuthorizationException()

        val w = review.imageUrl.split("/")
        val filename = w[w.lastIndex]
        println(filename)
        imageService.delete(filename, "review")

        // create file name
        val words = file.originalFilename?.split(".") ?: throw ImageNotFoundException()
        val key = UUID.randomUUID().toString() + "." + words[words.lastIndex]


        val imageUrl = imageService.upload(file, key, "review")
        val sentiment: ReviewSentiment =
            if (dto.content == review.content) ReviewSentiment.valueOf(sentimentService.analyze(dto.content).sentiment)
            else review.sentiment

        review.update(
            content = dto.content,
            features = dto.features,
            imageUrl = imageUrl,
            sentiment = sentiment
        )
        return ReviewDTO.from(reviewRepository.save(review))
    }
}