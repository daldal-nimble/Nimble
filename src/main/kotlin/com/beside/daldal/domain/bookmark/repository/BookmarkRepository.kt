package com.beside.daldal.domain.bookmark.repository

import com.beside.daldal.domain.bookmark.entity.Bookmark
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BookmarkRepository :MongoRepository<Bookmark, String>{
}