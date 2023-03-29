package com.beside.daldal.domain.favourite.repository

import com.beside.daldal.domain.favourite.entity.Favourite
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface FavoriteRepository : MongoRepository<Favourite, String> {
    fun findByMemberIdAndReviewId(memberId: String, reviewId: String): Favourite?
    fun existsByMemberIdAndReviewId(memberId: String, reviewId: String):Boolean
}