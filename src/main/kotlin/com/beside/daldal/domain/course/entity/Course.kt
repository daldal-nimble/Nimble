package com.beside.daldal.domain.course.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Course(
    @Id
    var id : String? = null,
    var memberId :String,
    var name : String,
    var distance : Long,// m 단위
    var duration: Long,
    var bookmark : Long = 0,
    var points : List<Map<String, Any?>>,
){

    fun bookmarkUp(){
        synchronized(this){
            bookmark+=1
        }
    }
    fun bookmarkDown(){
        synchronized(this){
            bookmark-=1
        }
    }



    override fun toString(): String =
        "Course(id=$id, memberId='$memberId', name='$name', distance=$distance, duration=$duration, points=$points)"
}