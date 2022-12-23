package com.gmcn.tokens.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.gmcn.tokens.errors.UnauthorizedApiException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
//import javax.servlet.FilterChain
//import javax.servlet.http.HttpServletRequest
//import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var applicationStatus: com.gmcn.tokens.ApplicationStatus

    @Autowired
    lateinit var configProperties: com.gmcn.tokens.ConfigProperties

    @Override
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.method == HttpMethod.OPTIONS.toString()) {
            return filterChain.doFilter(request, response)
        }

        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION) ?: ""

        val jwt = authorizationHeader
            .substringAfter("Bearer ")
            .substringAfter("bearer ")

        try {
            val jwtBody = validateJwt(jwt)

            val results = "/api/users/([^/]*).*".toRegex()
                .find(request.servletPath)?.destructured?.toList()
                ?: return respondWithUnauthorized(response)

            if (results.isEmpty()) {
                return respondWithUnauthorized(response)
            }

            val userId = results.first()

            if (jwtBody.subject != userId) {
                return respondWithUnauthorized(response)
            }

            applicationStatus.authorizedUserId = jwtBody.subject
        } catch (e: Exception) {
            return respondWithUnauthorized(response)
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return !request.servletPath.startsWith("/api/users/")
    }

    private fun validateJwt(jwt: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(configProperties.getTokenKey())
            .build()
            .parseClaimsJws(jwt).body
    }

    private fun respondWithUnauthorized(response: HttpServletResponse) {
        val apiErrorDTO = UnauthorizedApiException().toDTO()

        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.write(objectMapper.writeValueAsString(apiErrorDTO))
    }
}