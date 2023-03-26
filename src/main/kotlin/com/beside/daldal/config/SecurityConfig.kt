package com.beside.daldal.config

import com.beside.daldal.jwt.JwtAccessDeniedHandler
import com.beside.daldal.jwt.JwtAuthenticationEntryPoint
import com.beside.daldal.jwt.JwtSecurityConfig
import com.beside.daldal.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenProvider: JwtTokenProvider,
    private val accessDeniedHandler: JwtAccessDeniedHandler,
    private val authenticationEntryPoint: JwtAuthenticationEntryPoint
) {

    @Bean
    fun configure(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(
                "/api/v1/auth/login", // 임시,
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger/**",
                "/swagger-resources/**"
            )
        }
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.formLogin().disable()
        http.httpBasic().disable()
        http.exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler)
            .authenticationEntryPoint(authenticationEntryPoint)

        http.authorizeHttpRequests { auth ->
            auth.requestMatchers("/api/v1/auth/login").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
        }.apply(JwtSecurityConfig(tokenProvider))

        return http.build()
    }
}