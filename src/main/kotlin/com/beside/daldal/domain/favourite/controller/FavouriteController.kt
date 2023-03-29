package com.beside.daldal.domain.favourite.controller

import com.beside.daldal.domain.favourite.service.FavouriteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/favourite")
class FavouriteController(
    private val favouriteService: FavouriteService
) {
    @PostMapping("/up/{reviewId}")
    fun bookmarkUp(@PathVariable reviewId: String, principal: Principal): ResponseEntity<String> =
        ResponseEntity.ok(favouriteService.favouriteUp(principal.name, reviewId))

    @PostMapping("/down/{reviewId}")
    fun bookmarkDown(@PathVariable reviewId: String, principal: Principal): ResponseEntity<String> =
        ResponseEntity.ok(favouriteService.favouriteDown(principal.name, reviewId))
}