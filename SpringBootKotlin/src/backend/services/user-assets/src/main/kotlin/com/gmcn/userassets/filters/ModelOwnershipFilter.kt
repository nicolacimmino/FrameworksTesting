package com.gmcn.userassets.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.gmcn.userassets.daos.OwnedModelDAO
import com.gmcn.userassets.errors.ResourceNotFoundApiException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class ModelOwnershipFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private var modelPathTokensMap = mapOf(
        "accounts" to OwnedModelDAO.OwnedModelTypes.ACCOUNT
    )

    private lateinit var modelPathToken: String

    @Override
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.method == HttpMethod.OPTIONS.toString()) {
            return filterChain.doFilter(request, response)
        }

        val result = Regex("/api/users/([^/]*)/$modelPathToken/([^/]*)").matchEntire(request.servletPath)
        val userId = result?.groupValues?.get(1) ?: ""
        val resourceId = result?.groupValues?.get(2) ?: ""

        val modelInstance = OwnedModelDAO.findOrNull(
            modelPathTokensMap[modelPathToken] ?: throw Exception("Unsupported owned resource $modelPathToken"),
            resourceId
        )

        if (modelInstance == null || !modelInstance.isOwnedBy(userId)) {
            return this.respondWithNotFound(response)
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        for (key in modelPathTokensMap.keys) {
            if (request.servletPath.matches(Regex("/api/users/[^/]+/$key/[^/]+"))) {
                modelPathToken = key

                return false
            }
        }
        return true
    }

    private fun respondWithNotFound(response: HttpServletResponse) {
        val apiErrorDTO = ResourceNotFoundApiException().toDTO()

        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_NOT_FOUND
        response.writer.write(objectMapper.writeValueAsString(apiErrorDTO))
    }
}