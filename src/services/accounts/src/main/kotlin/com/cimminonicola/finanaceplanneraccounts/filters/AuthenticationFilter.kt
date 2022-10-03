package com.cimminonicola.finanaceplanneraccounts.filters


import com.cimminonicola.finanaceplanneraccounts.ApplicationStatus
import com.cimminonicola.finanaceplanneraccounts.errors.UnauthorizedApiException
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class AuthenticationFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var applicationStatus: ApplicationStatus

    @Override
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION) ?: ""

        val jwt = authorizationHeader
            .substringAfter("Bearer ")
            .substringAfter("bearer ")

        try {
            var jwtBody = this.validateJwt(jwt)

            var results = "\\/api\\/users\\/([^\\/]*).*".toRegex()
                .find(request.servletPath)?.destructured?.toList()
                ?: return this.respondWithUnathorized(response)

            if (results.isEmpty()) {
                return this.respondWithUnathorized(response)
            }

            val userId = results.first()

            if (jwtBody.subject != userId) {
                return this.respondWithUnathorized(response)
            }

            this.applicationStatus.authorizedUserId = jwtBody.subject
        } catch (e: Exception) {
            return this.respondWithUnathorized(response)
        }

        filterChain.doFilter(request, response);
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return !request.servletPath.startsWith("/api/users/")
    }

    private fun validateJwt(jwt: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(this.applicationStatus.getJWTKey())
            .build()
            .parseClaimsJws(jwt).body
    }

    private fun respondWithUnathorized(response: HttpServletResponse) {
        val apiErrorDTO = UnauthorizedApiException().toDTO()

        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.write(this.objectMapper.writeValueAsString(apiErrorDTO))
    }
}