package com.beside.daldal.domain.course.controller

import com.beside.daldal.domain.course.dto.CourseComplexDTO
import com.beside.daldal.domain.course.dto.CourseCreateDTO
import com.beside.daldal.domain.course.dto.CourseReadDTO
import com.beside.daldal.domain.course.dto.CourseUpdateDTO
import com.beside.daldal.domain.course.service.CourseService
import com.beside.daldal.shared.dto.CommonResponse
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

@RestController
@RequestMapping("/api/v1/course")
class CourseController(
    private val courseService: CourseService
) {

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
        description = "자신의 코스를 뛴 코스, 안뛴 코스로 구분되어 조회할 수 있습니다.",
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
                            implementation = CourseComplexDTO::class
                        )
                    )
                )]
            ),
        ]
    )
    @GetMapping("")
    fun findMyCourses(principal: Principal): ResponseEntity<List<CourseComplexDTO>> {
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
        @PathVariable("courseId") courseId: String
    ): ResponseEntity<String> {
        courseService.delete(courseId)
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
    @PutMapping("")
    fun update(@RequestBody dto: CourseUpdateDTO, principal: Principal): ResponseEntity<CourseReadDTO> {
        val email = principal.name
        return ResponseEntity.ok(courseService.update(email, dto))
    }

    @Operation(
        operationId = "popular",
        summary = "인기 있는 코스를 9개 조회",
        description = "스크랩 수가 높은 것을 기준으로 9개 조회합니다.",
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
    @GetMapping("/popular")
    fun popular(): ResponseEntity<List<CourseReadDTO>> {
        return ResponseEntity.ok().body(courseService.popular())
    }

    @Operation(
        operationId = "scrap",
        summary = "course id를 이용해서 특정 코스를 스크랩합니다.",
        description = "코스는 id를 기준으로 유일하게 존재해야합니다. 중복 course id 안됨",
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
                            implementation = CommonResponse::class
                        )
                    )
                )]
            ),
        ]
    )
    @PostMapping("/scrap/{courseId}")
    fun scrap(@PathVariable courseId: String, principal: Principal): ResponseEntity<CommonResponse> {
        val email: String = principal.name
        courseService.scrap(email, courseId)
        return ResponseEntity.ok(
            CommonResponse(
                message = "성공적으로 스크랩 했습니다.",
                code = "SCRAP_SUCCESS",
                status = 200
            )
        )
    }
}