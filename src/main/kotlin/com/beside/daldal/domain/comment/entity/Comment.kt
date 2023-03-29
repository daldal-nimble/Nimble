package com.beside.daldal.domain.comment.entity

import com.beside.daldal.shared.entity.BaseTimeEntity
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("comment")
class Comment(
    var id :String? = null,
    var memberId : String,
    var content : String
):BaseTimeEntity(){
    fun delete(){
        deletedAt = LocalDateTime.now()
    }
    fun isDeleted():Boolean = deletedAt != null
    fun update(content: String) {
        this.content = content
    }
}