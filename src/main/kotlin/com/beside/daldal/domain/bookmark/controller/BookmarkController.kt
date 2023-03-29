package com.beside.daldal.domain.bookmark.controller

import com.beside.daldal.domain.bookmark.service.BookmarkService
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
    @PostMapping("/up/{courseId}")
    fun up(principal: Principal, @PathVariable courseId: String): String {
        return bookmarkService.bookmarkUp(principal.name, courseId)
    }

    @PostMapping("/down/{courseId}")
    fun down(principal: Principal, @PathVariable courseId: String): String =
        bookmarkService.bookmarkDown(principal.name, courseId)
}