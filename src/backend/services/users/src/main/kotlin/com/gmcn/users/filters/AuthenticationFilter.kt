package com.gmcn.users.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.gmcn.users.errors.UnauthorizedApiException
import com.gmcn.users.remoteservices.TokensService
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
    lateinit var interServiceMessagesSender: TokensService

    @Override
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        if (request.method == HttpMethod.OPTIONS.toString()) {
            return filterChain.doFilter(request, response)
        }

        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION) ?: ""

        val jwt = authorizationHeader.substringAfter("Bearer ").substringAfter("bearer ")

        val authResponse = interServiceMessagesSender.validateToken(jwt) ?: return respondWithUnauthorized(response)

        if (!authResponse.valid) {
            return respondWithUnauthorized(response)
        }

        val results = "/api/users/([^/]*).*".toRegex()
            .find(request.servletPath)?.destructured?.toList()
            ?: return respondWithUnauthorized(response)

        if (results.isEmpty()) {
            return respondWithUnauthorized(response)
        }

        val userId = results.first()

        if (authResponse.subject != userId) {
            return respondWithUnauthorized(response)
        }

        request.setAttribute("auth.user_id", authResponse.subject)

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return !request.servletPath.startsWith("/api/users/")
    }

    private fun respondWithUnauthorized(response: HttpServletResponse) {
        val apiErrorDTO = UnauthorizedApiException().toDTO()

        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.write(objectMapper.writeValueAsString(apiErrorDTO))
    }
}