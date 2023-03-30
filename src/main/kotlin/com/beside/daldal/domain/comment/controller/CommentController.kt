package com.beside.daldal.domain.comment.controller

import com.beside.daldal.domain.comment.dto.CommentCreateDTO
import com.beside.daldal.domain.comment.dto.CommentDTO
import com.beside.daldal.domain.comment.dto.CommentUpdateDTO
import com.beside.daldal.domain.comment.service.CommentService
import com.beside.daldal.domain.review.service.ReviewService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    private val commentService: CommentService,
    private val reviewService: ReviewService
) {
    // swagger
    @Operation(
        operationId = "createComment",
        summary = "댓글 생성",
        description = "댓글을 생성합니다. 댓글을 생성하면 해당 리뷰에 댓글이 추가됩니다.",
        tags = ["comment"]
    )
    @ApiResponses(
        value =
        [
            ApiResponse(
                responseCode = "200",
                description = "success",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CommentDTO::class)
                )]
            ),
        ]
    )
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

    @Operation(
        operationId = "deleteComment",
        summary = "댓글 삭제",
        description = "댓글을 삭제합니다. 댓글을 삭제하면 해당 리뷰에 댓글이 삭제됩니다.",
        tags = ["comment"]
    )
    @ApiResponses(
        value =
        [
            ApiResponse(
                responseCode = "200",
                description = "success",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = String::class)
                )]
            ),
        ]
    )
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

    @Operation(
        operationId = "updateComment",
        summary = "댓글 수정",
        description = "댓글을 수정합니다. 댓글을 수정하면 해당 리뷰에 댓글이 수정됩니다.",
        tags = ["comment"]
    )
    @ApiResponses(
        value =
        [
            ApiResponse(
                responseCode = "200",
                description = "success",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CommentDTO::class)
                )]
            ),
        ]
    )
    @PutMapping("/{reviewId}/{commentId}")
    fun updateComment(
        principal: Principal,
        @PathVariable reviewId: String,
        @PathVariable commentId: String,
        @RequestBody dto: CommentUpdateDTO
    ): ResponseEntity<CommentDTO> {
        val updated = commentService.updateComment(commentId, dto)
        reviewService.updateComment(reviewId, commentId, updated.content)
        return ResponseEntity.ok(updated)
    }
}