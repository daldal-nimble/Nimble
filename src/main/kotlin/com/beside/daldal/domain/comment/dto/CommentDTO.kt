package com.beside.daldal.domain.comment.dto

import com.beside.daldal.domain.comment.entity.Comment
import com.beside.daldal.domain.comment.error.CommentNotFoundException

class CommentDTO (
    val id :String,
    val memberId : String,
    val content : String
){
    companion object {
        fun from(comment: Comment) = CommentDTO(
            id = comment.id ?: throw CommentNotFoundException(),
            memberId = comment.memberId,
            content = comment.content
        )
    }
}
