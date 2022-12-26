package com.gmcn.users.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.gmcn.users.ApplicationStatus
import com.gmcn.users.ConfigProperties
import com.gmcn.users.errors.UnauthorizedApiException
import com.gmcn.users.isc.InterServiceMessagesSender
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

@Component
class AuthenticationFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var applicationStatus: ApplicationStatus

    @Autowired
    lateinit var configProperties: ConfigProperties

    @Autowired
    lateinit var interServiceMessagesSender: InterServiceMessagesSender

    @Override
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        if (request.method == HttpMethod.OPTIONS.toString()) {
            return filterChain.doFilter(request, response)
        }

        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION) ?: ""

        val jwt = authorizationHeader.substringAfter("Bearer ").substringAfter("bearer ")

        val authUserId = interServiceMessagesSender.validateToken(jwt) ?: return respondWithUnauthorized(response)

        val results = "/api/users/([^/]*).*".toRegex()
            .find(request.servletPath)?.destructured?.toList()
            ?: return respondWithUnauthorized(response)

        if (results.isEmpty()) {
            return respondWithUnauthorized(response)
        }

        val userId = results.first()

        if (authUserId != userId) {
            return respondWithUnauthorized(response)
        }

        applicationStatus.authorizedUserId = authUserId

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return !request.servletPath.startsWith("/api/users/")
    }

    private fun validateJwt(jwt: String): Claims {
        return Jwts.parserBuilder().setSigningKey(configProperties.getTokenKey()).build().parseClaimsJws(jwt).body
    }

    private fun respondWithUnauthorized(response: HttpServletResponse) {
        val apiErrorDTO = UnauthorizedApiException().toDTO()

        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.write(objectMapper.writeValueAsString(apiErrorDTO))
    }
}