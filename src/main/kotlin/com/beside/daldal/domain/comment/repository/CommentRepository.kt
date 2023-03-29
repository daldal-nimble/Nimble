package com.beside.daldal.domain.comment.repository

import com.beside.daldal.domain.comment.entity.Comment
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : MongoRepository<Comment, String>{

}
