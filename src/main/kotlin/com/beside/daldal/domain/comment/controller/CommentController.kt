package com.beside.daldal.domain.comment.controller

import com.beside.daldal.domain.comment.dto.CommentCreateDTO
import com.beside.daldal.domain.comment.dto.CommentDTO
import com.beside.daldal.domain.comment.dto.CommentUpdateDTO
import com.beside.daldal.domain.comment.service.CommentService
import com.beside.daldal.domain.review.service.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    private val commentService: CommentService,
    private val reviewService: ReviewService
) {

    @PostMapping("/{reviewId}")
    fun createComment(
        principal: Principal,
        @PathVariable reviewId: String,
        dto: CommentCreateDTO
    ): ResponseEntity<CommentDTO> {
        val commentDTO = commentService.createComment(principal.name, dto)
        reviewService.addComment(principal.name, reviewId, commentDTO.content)
        return ResponseEntity.ok(commentDTO)
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        principal: Principal,
        @PathVariable commentId: String
    ): ResponseEntity<String> {
//        this.getComment(principal.name, commentId)
        commentService.deleteComment(commentId)
        return ResponseEntity.ok(commentId)
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        principal: Principal,
        @PathVariable commentId: String,
        dto: CommentUpdateDTO
    ): ResponseEntity<CommentDTO> {
        val commentDTO = commentService.updateComment(commentId, dto)
        return ResponseEntity.ok(commentDTO)
    }
}