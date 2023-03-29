package com.beside.daldal.domain.bookmark.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("bookmark")
class Bookmark(
    @Id
    var id: String? = null,
    var memberId: String,
    var reviewId: String
)