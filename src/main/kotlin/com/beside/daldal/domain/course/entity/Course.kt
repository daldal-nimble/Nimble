package com.beside.daldal.domain.course.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Duration

@Document
class Course(
    @Id
    var id : String? = null,
    var memberId :String,
    var name : String,
    var distance : Long,// m 단위
    var duration: Duration,
    var points : List<Map<String, Any?>>,
    var scarp:Long = 0,
){

    override fun toString(): String =
        "Course(id=$id, memberId='$memberId', name='$name', distance=$distance, duration=$duration, points=$points)"

    fun scrapUp(){
        // 해당 동시성 제어는 단일 서버를 기준으로 동작합니다. 따라서 레플리카를 이용한 분산서버를 구현할 때에는 반드시 동시성 제어를 다시 구현해야합니다.
        synchronized(this){
            this.scarp += 1
        }
    }
}