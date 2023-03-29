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
        @RequestBody dto: CommentCreateDTO
    ): ResponseEntity<CommentDTO> {
        val commentDTO = commentService.createComment(principal.name, dto)
        reviewService.addComment(reviewId, commentDTO)
        return ResponseEntity.ok(commentDTO)
    }

    @DeleteMapping("/{reviewId}/{commentId}")
    fun deleteComment(
        principal: Principal,
        @PathVariable reviewId: String,
        @PathVariable commentId: String
    ): ResponseEntity<String> {
        reviewService.deleteComment(reviewId, commentId)
        commentService.deleteComment(commentId)
        return ResponseEntity.ok(commentId)
    }

    @PutMapping("/{reviewId}/{commentId}")
    fun updateComment(
        principal: Principal,
        @PathVariable reviewId: String,
        @PathVariable commentId: String,
        @RequestBody dto: CommentUpdateDTO
    ): ResponseEntity<CommentDTO> {
        val dto = commentService.updateComment(commentId, dto)
        reviewService.updateComment(reviewId, commentId, dto.content)
        return ResponseEntity.ok(dto)
    }
}