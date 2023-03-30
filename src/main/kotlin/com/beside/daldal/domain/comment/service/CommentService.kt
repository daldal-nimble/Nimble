package com.beside.daldal.domain.comment.service

import com.beside.daldal.domain.comment.dto.CommentCreateDTO
import com.beside.daldal.domain.comment.dto.CommentDTO
import com.beside.daldal.domain.comment.dto.CommentUpdateDTO
import com.beside.daldal.domain.comment.error.CommentNotFoundException
import com.beside.daldal.domain.comment.repository.CommentRepository
import com.beside.daldal.domain.member.error.MemberNotFoundException
import com.beside.daldal.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun createComment(email: String, dto: CommentCreateDTO): CommentDTO {
        val memberId = memberRepository.findByEmail(email)?.id ?: throw MemberNotFoundException()
        val comment = dto.toEntity(memberId, dto.content)
        val savedComment = commentRepository.save(comment)
        return CommentDTO.from(savedComment)
    }


    @Transactional
    fun deleteComment(commentId: String){
        val comment = commentRepository.findById(commentId).orElseThrow { throw CommentNotFoundException() }
        commentRepository.delete(comment)
    }

    @Transactional
    fun updateComment(commentId: String, dto: CommentUpdateDTO): CommentDTO {
        val comment = commentRepository.findById(commentId).orElseThrow { throw CommentNotFoundException() }
        comment.update(dto.content)
        commentRepository.save(comment)
        return CommentDTO.from(comment)
    }
}
