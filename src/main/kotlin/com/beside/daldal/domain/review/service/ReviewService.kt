package com.beside.daldal.domain.review.service


import com.beside.daldal.domain.comment.dto.CommentDTO
import com.beside.daldal.domain.comment.entity.Comment
import com.beside.daldal.domain.course.error.CourseNotFoundException
import com.beside.daldal.domain.course.repository.CourseRepository
import com.beside.daldal.domain.image.error.ImageNotFoundException
import com.beside.daldal.domain.image.service.ImageService
import com.beside.daldal.domain.member.error.MemberNotFoundException
import com.beside.daldal.domain.member.repository.MemberRepository
import com.beside.daldal.domain.review.dto.ReviewCreateDTO
import com.beside.daldal.domain.review.dto.ReviewDTO
import com.beside.daldal.domain.review.dto.ReviewReadDTO
import com.beside.daldal.domain.review.dto.ReviewUpdateDTO
import com.beside.daldal.domain.review.entity.Review
import com.beside.daldal.domain.review.entity.ReviewFeature
import com.beside.daldal.domain.review.entity.ReviewSentiment
import com.beside.daldal.domain.review.error.ReviewAuthorizationException
import com.beside.daldal.domain.review.error.ReviewNotFoundException
import com.beside.daldal.domain.review.repository.ReviewRepository
import com.beside.daldal.domain.sentiment.service.SentimentService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class ReviewService(
    private val memberRepository: MemberRepository,
    private val reviewRepository: ReviewRepository,
    private val courseRepository: CourseRepository,
    private val imageService: ImageService,
    private val sentimentService: SentimentService
) {
    @Transactional(readOnly = true)
    fun findMyReview(email: String): MutableList<ReviewReadDTO> {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val memberId = member.id ?: throw MemberNotFoundException()
        val reviews = reviewRepository.findAllByMemberId(memberId)
        val result = mutableListOf<ReviewReadDTO>()
        for (review in reviews) {
            val course = courseRepository.findById(review.courseId).orElseThrow { throw CourseNotFoundException() }
            result.add(ReviewReadDTO.from(review, course, member))
        }
        return result
    }

    @Transactional(readOnly = true)
    fun findById(email: String, reviewId: String): ReviewReadDTO {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val review = reviewRepository.findById(reviewId).orElseThrow { throw ReviewNotFoundException() }
        val course = courseRepository.findById(review.courseId).orElseThrow { throw CourseNotFoundException() }
        return ReviewReadDTO.from(review, course, member)
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
    fun deleteReview(email : String, reviewId: String) {
        val review = reviewRepository.findById(reviewId).orElseThrow { throw ReviewNotFoundException() }
        val memberId = memberRepository.findByEmail(email)?.id ?: throw MemberNotFoundException()
        if(review.memberId != memberId)
            throw ReviewAuthorizationException()

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

    @Transactional(readOnly = true)
    fun findPopularReview(email: String): List<ReviewReadDTO> {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()

        return reviewRepository.findPopularReview().map { review ->
            val course = courseRepository.findById(review.courseId).orElseThrow { throw CourseNotFoundException() }
            ReviewReadDTO.from(review, course, member)
        }
    }

    @Transactional
    fun addComment(reviewId: String, dto: CommentDTO) {
        val review = reviewRepository.findById(reviewId).orElseThrow { throw ReviewNotFoundException() }
        // add Comment to review
        val comment = Comment(dto.id, dto.memberId, dto.content)
        review.addComment(comment)
        reviewRepository.save(review)
    }

    @Transactional
    fun deleteComment(reviewId: String, commentId: String) {
        val review = reviewRepository.findById(reviewId).orElseThrow { throw ReviewNotFoundException() }
        review.deleteComment(commentId)
        reviewRepository.save(review)
    }

    @Transactional
    fun updateComment(reviewId: String, commentId: String, content: String) {
        val review = reviewRepository.findById(reviewId).orElseThrow { throw ReviewNotFoundException() }
        review.updateComment(commentId, content)
        reviewRepository.save(review)
    }

    @Transactional(readOnly = true)
    fun findAllByFiltering(email: String, features: List<ReviewFeature>, page: Int, size: Int): Page<ReviewReadDTO> {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))

        val reviews = if (features.isEmpty()) {
            reviewRepository.findAllByFilter(ReviewFeature.values().toList(), pageable)
        } else {
            reviewRepository.findAllByFilter(features, pageable)
        }

        return reviews.map { review ->
            val course = courseRepository.findById(review.courseId).orElseThrow { throw CourseNotFoundException() }
            ReviewReadDTO.from(review, course, member)
        }
    }
}