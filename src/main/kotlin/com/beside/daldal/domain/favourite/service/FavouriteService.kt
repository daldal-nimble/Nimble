package com.beside.daldal.domain.favourite.service

import com.beside.daldal.domain.favourite.entity.Favourite
import com.beside.daldal.domain.favourite.error.FavouriteAlreadyExistException
import com.beside.daldal.domain.favourite.error.FavouriteNotFoundException
import com.beside.daldal.domain.favourite.repository.FavoriteRepository
import com.beside.daldal.domain.member.error.MemberNotFoundException
import com.beside.daldal.domain.member.repository.MemberRepository
import com.beside.daldal.domain.review.entity.Review
import com.beside.daldal.domain.review.error.ReviewNotFoundException
import com.beside.daldal.domain.review.repository.ReviewRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FavouriteService(
    private val favoriteRepository: FavoriteRepository,
    private val reviewRepository: ReviewRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun favouriteUp(email: String, reviewId: String): String {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val memberId = member.id ?: throw MemberNotFoundException()
        val review: Review = reviewRepository.findById(reviewId).orElseThrow { throw ReviewNotFoundException() }
        if (favoriteRepository.existsByMemberIdAndReviewId(memberId, reviewId)) throw FavouriteAlreadyExistException()
        review.favoriteUp()
        member.favorite(reviewId)
        reviewRepository.save(review)
        memberRepository.save(member)
        return favoriteRepository.save(Favourite(memberId = memberId, reviewId = reviewId)).id
            ?: throw FavouriteNotFoundException()
    }


    @Transactional
    fun favouriteDown(email: String, reviewId: String): String {
        val member = memberRepository.findByEmail(email) ?: throw MemberNotFoundException()
        val memberId = member.id ?: throw MemberNotFoundException()
        val review = reviewRepository.findById(reviewId).orElseThrow { throw ReviewNotFoundException() }
        val favoriteId =
            favoriteRepository.findByMemberIdAndReviewId(memberId, reviewId)?.id ?: throw FavouriteNotFoundException()

        // bookmark 가 있는 경우
        review.favoriteDown()
        member.unfavorite(reviewId)
        favoriteRepository.deleteById(favoriteId)
        reviewRepository.save(review)
        memberRepository.save(member)
        return favoriteId
    }
}