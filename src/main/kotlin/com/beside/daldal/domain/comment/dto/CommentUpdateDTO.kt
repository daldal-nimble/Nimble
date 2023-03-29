package com.beside.daldal.domain.comment.dto

import com.beside.daldal.domain.comment.entity.Comment

class CommentUpdateDTO(
    val content: String
) {
    fun toEntity(entity: Comment): Comment {
        return Comment(
            id = entity.id,
            memberId = entity.memberId,
            content = content
        )
    }
}