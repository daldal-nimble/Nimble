package com.beside.daldal.domain.favourite.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("favourite")
class Favourite(
    @Id
    var id: String? = null,
    var memberId: String,
    var reviewId: String
)