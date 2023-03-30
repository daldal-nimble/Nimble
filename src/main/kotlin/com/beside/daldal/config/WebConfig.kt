package com.beside.daldal.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


// 개발을 위해서 잠시 끄기
//@Configuration
//class WebConfig : WebMvcConfigurer{
//    override fun addCorsMappings(registry: CorsRegistry) {
//        registry.addMapping("/**")
//            .allowedOrigins("https://daldal.vercel.app")
//            .allowedMethods("*")
//            .maxAge(3000)
//    }
//}