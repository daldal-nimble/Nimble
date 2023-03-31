package com.beside.daldal.domain.course.controller

import com.beside.daldal.domain.course.dto.CourseCreateDTO
import com.beside.daldal.domain.course.dto.CourseReadDTO
import com.beside.daldal.domain.course.dto.CourseUpdateDTO
import com.beside.daldal.domain.course.service.CourseService
import com.beside.daldal.shared.exception.dto.ErrorCode
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/course")
class CourseController(
    private val courseService: CourseService
) {
    @Operation(
        operationId = "randomCourse",
        summary = "랜덤 코스 조회",
        description = "인기 코스를 조회할 수 있습니다.",
        tags = ["course"]
    )
    @ApiResponses(
        value =
        [
            ApiResponse(
                responseCode = "200",
                description = "success",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CourseReadDTO::class)
                )]
            ),
        ]
    )
    @GetMapping("/random")
    fun findRandomCourse(): ResponseEntity<CourseReadDTO> =
        ResponseEntity.ok(courseService.findPopularCourse())

    @Operation(
        operationId = "findById",
        summary = "id를 이용한 조회",
        description = "id를 이용해서 코스를 조회할 수 있습니다.",
        tags = ["course"]
    )
    @ApiResponses(
        value =
        [
            ApiResponse(
                responseCode = "200",
                description = "success",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CourseReadDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "course, member not found exception",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorCode::class)
                )]
            ),
        ]
    )
    @GetMapping("/{courseId}")
    fun findById(@PathVariable courseId: String): ResponseEntity<CourseReadDTO> =
        ResponseEntity.ok(courseService.findById(courseId))

    @Operation(
        operationId = "findMyCourses",
        summary = "자신의 코스 조회하기",
        description = "",
        tags = ["course"]
    )
    @ApiResponses(
        value =
        [
            ApiResponse(
                responseCode = "200",
                description = "success",
                content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(
                        schema = Schema(
                            implementation = CourseReadDTO::class
                        )
                    )
                )]
            ),
        ]
    )
    @GetMapping("")
    fun findMyCourses(principal: Principal): ResponseEntity<List<CourseReadDTO>> {
        val email = principal.name
        return ResponseEntity.ok(courseService.findMyCourses(email))
    }

    @Operation(
        operationId = "create",
        summary = "자신의 코스 생성하기",
        description = "코스를 생성하고 자신의 코스에 넣습니다.",
        tags = ["course"]
    )
    @ApiResponses(
        value =
        [
            ApiResponse(
                responseCode = "200",
                description = "success",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CourseReadDTO::class)
                )]
            ),
        ]
    )
    @PostMapping("")
    fun create(
        @RequestBody dto: CourseCreateDTO, principal: Principal
    ): ResponseEntity<CourseReadDTO> {
        val email: String = principal.name
        return ResponseEntity.ok(courseService.create(email, dto))
    }

    @Operation(
        operationId = "delete",
        summary = "자신의 코스 삭제하기",
        description = "자신이 생성한 코스만 삭제할 수 있습니다.",
        tags = ["course"]
    )
    @ApiResponses(
        value =
        [
            ApiResponse(
                responseCode = "200",
                description = "success return deleted course id",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = String::class)
                )]
            ),
        ]
    )
    @DeleteMapping("/{courseId}")
    fun delete(
        principal: Principal,
        @PathVariable("courseId") courseId: String
    ): ResponseEntity<String> {
        val email: String = principal.name
        courseService.delete(email, courseId)
        return ResponseEntity.ok(courseId)
    }

    @Operation(
        operationId = "update",
        summary = "자신의 코스 수정하기",
        description = "자신이 생성한 코스만 수정할 수 있습니다.",
        tags = ["course"]
    )
    @ApiResponses(
        value =
        [
            ApiResponse(
                responseCode = "200",
                description = "success",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CourseReadDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "member, course not found exception",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorCode::class)
                )]
            ),
        ]
    )
    @PutMapping("/{courseId}")
    fun update(
        @RequestBody dto: CourseUpdateDTO,
        @PathVariable("courseId") courseId: String,
        principal: Principal
    ): ResponseEntity<CourseReadDTO> {
        val email = principal.name
        return ResponseEntity.ok(courseService.update(email, courseId, dto))
    }
}