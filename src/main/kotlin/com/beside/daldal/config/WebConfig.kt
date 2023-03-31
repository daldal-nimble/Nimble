package com.beside.daldal.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


//개발을 위해서 잠시 끄기
@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*", "Authorization")
            .maxAge(3000)
        registry.addMapping("/api/v1/course/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*", "Authorization")
            .maxAge(3000)
        registry.addMapping("/api/v1/review/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*", "Authorization")
            .maxAge(3000)
        registry.addMapping("/api/v1/member/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*", "Authorization")
            .maxAge(3000)
        registry.addMapping("/api/v1/bookmark/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*", "Authorization")
            .maxAge(3000)
        registry.addMapping("/api/v1/favourite/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*", "Authorization")
            .maxAge(3000)
        registry.addMapping("/api/v1/comments/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*", "Authorization")
            .maxAge(3000)
    }
}