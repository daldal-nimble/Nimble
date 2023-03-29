package com.beside.daldal.domain.comment.dto

import com.beside.daldal.domain.comment.entity.Comment

class CommentDTO (
    val id :String?,
    val memberId : String,
    val content : String
){
    companion object {
        fun from(comment: Comment) = CommentDTO(
            id = comment.id,
            memberId = comment.memberId,
            content = comment.content
        )
    }
}
