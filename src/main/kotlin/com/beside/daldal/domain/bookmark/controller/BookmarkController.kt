package com.beside.daldal.domain.bookmark.controller

import com.beside.daldal.domain.bookmark.service.BookmarkService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/bookmark")
class BookmarkController(
    private val bookmarkService: BookmarkService
) {
    @PostMapping("/up/{reviewId}")
    fun bookmarkUp(@PathVariable reviewId: String, principal: Principal): ResponseEntity<String> =
        ResponseEntity.ok(bookmarkService.bookmarkUp(principal.name, reviewId))

    @PostMapping("/down/{reviewId}")
    fun bookmarkDown(@PathVariable reviewId: String, principal: Principal): ResponseEntity<String> =
        ResponseEntity.ok(bookmarkService.bookmarkDown(principal.name, reviewId))
}