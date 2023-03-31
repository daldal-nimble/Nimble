package com.beside.daldal.domain.bookmark.controller

import com.beside.daldal.domain.bookmark.service.BookmarkService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*
import java.security.Principal

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/bookmark")
class BookmarkController(
    private val bookmarkService: BookmarkService
) {
    @Operation(
        operationId = "up",
        summary = "북마크 업",
        description = "해당 사용자가 북마크 업되고 코스의 북마크 카운트가 증가합니다.",
        tags = ["bookmark"]
    )
    @PostMapping("/up/{courseId}")
    fun up(principal: Principal, @PathVariable courseId: String): String {
        return bookmarkService.bookmarkUp(principal.name, courseId)
    }

    @Operation(
        operationId = "down",
        summary = "북마크 다운",
        description = "해당 사용자가 북마크 다운되고 코스의 북마크 카운트가 하락합니다.",
        tags = ["bookmark"]
    )@PostMapping("/down/{courseId}")
    fun down(principal: Principal, @PathVariable courseId: String): String =
        bookmarkService.bookmarkDown(principal.name, courseId)
}