package com.beside.daldal.domain.bookmark.service

import com.beside.daldal.domain.bookmark.entity.Bookmark
import com.beside.daldal.domain.bookmark.error.BookmarkNotFoundException
import com.beside.daldal.domain.bookmark.repository.BookmarkRepository
import com.beside.daldal.domain.member.error.MemberNotFoundException
import com.beside.daldal.domain.member.repository.MemberRepository
import com.beside.daldal.domain.review.error.ReviewNotFoundException
import com.beside.daldal.domain.review.repository.ReviewRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookmarkService(
    private val bookmarkRepository: BookmarkRepository,
    private val reviewRepository: ReviewRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun bookmarkUp(email: String, reviewId: String): String {
        val memberId = memberRepository.findByEmail(email)?.id ?: throw MemberNotFoundException()
        val review = reviewRepository.findById(reviewId).orElseThrow { throw ReviewNotFoundException() }
        review.bookmarkUp()
        reviewRepository.save(review)
        return bookmarkRepository.save(Bookmark(memberId = memberId, reviewId = reviewId)).id
            ?: throw BookmarkNotFoundException()
    }


    @Transactional
    fun bookmarkDown(email: String, reviewId: String): String {
        val memberId = memberRepository.findByEmail(email)?.id ?: throw MemberNotFoundException()
        val review = reviewRepository.findById(reviewId).orElseThrow { throw ReviewNotFoundException() }
        review.bookmarkDown()
        reviewRepository.save(review)
        return bookmarkRepository.save(Bookmark(memberId = memberId, reviewId = reviewId)).id
            ?: throw BookmarkNotFoundException()
    }
}