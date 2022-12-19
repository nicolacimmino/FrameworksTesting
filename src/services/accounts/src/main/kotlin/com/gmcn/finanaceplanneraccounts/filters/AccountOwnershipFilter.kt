package com.gmcn.finanaceplanneraccounts.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.gmcn.finanaceplanneraccounts.datasource.AccountDataSource
import com.gmcn.finanaceplanneraccounts.errors.ResourceNotFoundApiException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AccountOwnershipFilter : OncePerRequestFilter() {
    @Autowired
    private lateinit var accountsDataSource: AccountDataSource

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Override
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.method == HttpMethod.OPTIONS.toString()) {
            return filterChain.doFilter(request, response)
        }

        val result = Regex("/api/users/([^/]*)/accounts/([^/]*)").matchEntire(request.servletPath)
        val userId = result?.groupValues?.get(1) ?: ""
        val resourceId = result?.groupValues?.get(2) ?: ""

        val account = accountsDataSource.findByIdOrNull(resourceId)

        if (account?.userId != userId) {
            return this.respondWithNotFound(response)
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return !request.servletPath.matches(Regex("/api/users/[^/]+/accounts/[^/]+"))
    }

    private fun respondWithNotFound(response: HttpServletResponse) {
        val apiErrorDTO = ResourceNotFoundApiException().toDTO()

        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_NOT_FOUND
        response.writer.write(objectMapper.writeValueAsString(apiErrorDTO))
    }
}