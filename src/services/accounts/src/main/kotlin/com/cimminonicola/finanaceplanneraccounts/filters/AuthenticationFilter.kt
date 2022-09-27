package com.cimminonicola.finanaceplanneraccounts.filters


import com.cimminonicola.finanaceplanneraccounts.errors.UnauthorizedApiException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class AuthenticationFilter : OncePerRequestFilter() {
    @Autowired
    private val appContext: ApplicationContext? = null

    @Override
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        println("filter!")

        // TODO: if we throw here we get to FilterChainExceptionHandler but with a servletException, needs fixing.
        //throw UnauthorizedApiException()

        filterChain.doFilter(request, response);
    }
}