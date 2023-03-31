package com.beside.daldal.domain.review.controller

import com.beside.daldal.domain.review.dto.ReviewCreateDTO
import com.fasterxml.jackson.databind.ObjectMapper
import java.beans.PropertyEditorSupport


class ReviewRootEditor(private val objectMapper: ObjectMapper) : PropertyEditorSupport() {

    override fun setAsText(text: String?) {
        val t = text ?: throw IllegalArgumentException("글자가 없음")
        println(t)
        val dto = objectMapper.readValue(t,ReviewCreateDTO::class.java)
        value = dto
    }
}