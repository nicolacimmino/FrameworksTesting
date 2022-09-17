package com.cimminonicola.finanaceplanneraccounts.middleware

import com.cimminonicola.finanaceplanneraccounts.errors.InputInvalidApiException
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationMiddleware : OncePerRequestFilter() {

    @Override
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        // TODO: issue, this runs early and the handler is not setup yet
//        if (request.getParameter("user_id") != null) {
//            throw InputInvalidApiException("test parameter forbidden");
//        }

        filterChain.doFilter(request, response);
    }
}