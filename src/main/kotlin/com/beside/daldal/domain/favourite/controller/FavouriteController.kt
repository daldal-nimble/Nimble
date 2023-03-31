package com.beside.daldal.domain.favourite.controller

import com.beside.daldal.domain.favourite.service.FavouriteService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/favourite")
class FavouriteController(
    private val favouriteService: FavouriteService
) {
    @Operation(
        operationId = "favoriteUp",
        summary = "좋아요 업",
        description = "좋아요를 올리고 리뷰의 좋아요를 업시킵니다.",
        tags = ["favorite"]
    )
    @PostMapping("/up/{reviewId}")
    fun favoriteUp(@PathVariable reviewId: String, principal: Principal): ResponseEntity<String> =
        ResponseEntity.ok(favouriteService.favouriteUp(principal.name, reviewId))

    @Operation(
        operationId = "favoriteDown",
        summary = "좋아요 다운",
        description = "좋아요를 내리고 리뷰의 좋아요를 다운 시킵니다.",
        tags = ["favorite"]
    )
    @PostMapping("/down/{reviewId}")
    fun favoriteDown(@PathVariable reviewId: String, principal: Principal): ResponseEntity<String> =
        ResponseEntity.ok(favouriteService.favouriteDown(principal.name, reviewId))
}