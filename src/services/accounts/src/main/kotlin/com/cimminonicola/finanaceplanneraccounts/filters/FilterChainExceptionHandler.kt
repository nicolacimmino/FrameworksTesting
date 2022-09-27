package com.cimminonicola.finanaceplanneraccounts.filters

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class FilterChainExceptionHandler : OncePerRequestFilter() {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private val resolver: HandlerExceptionResolver? = null

    @Override
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response);
        } catch (e: Exception) {
            this.resolver?.resolveException(request, response, null, e)
        }
    }
}