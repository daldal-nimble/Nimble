package com.beside.daldal.domain.comment.dto

import com.beside.daldal.domain.comment.entity.Comment

class CommentCreateDTO(
    var content: String
) {
    fun toEntity(memberId: String, content: String): Comment {
        return Comment(
            memberId = memberId,
            content = content
        )
    }
}
