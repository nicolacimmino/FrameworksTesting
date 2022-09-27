package com.cimminonicola.finanaceplanneraccounts.errors

import com.cimminonicola.finanaceplanneraccounts.dtos.ApiErrorDTO
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class ApiErrorHandler {
    @ResponseBody
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun defaultHandler(exception: Exception): ApiErrorDTO {
        return this.render(exception.message ?: "Internal Error", "INTERNAL_ERROR")
    }

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundApiException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun resourceNotFoundHandler(exception: ResourceNotFoundApiException): ApiErrorDTO {
        return this.render(exception.message ?: "Not Found", "NOT_FOUND")
    }

    @ResponseBody
    @ExceptionHandler(InputInvalidApiException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun inputInvalidHandler(exception: InputInvalidApiException): ApiErrorDTO {
        return this.render(exception.message ?: "Input Invalid", "INPUT_INVALID")
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun missingParameterHandler(exception: MissingServletRequestParameterException): ApiErrorDTO {
        return this.render(exception.message, "MISSING_PARAMETER")
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedApiException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun missingParameterHandler(exception: UnauthorizedApiException): ApiErrorDTO {
        return this.render(exception.message ?: "Unauthorized", "UNAUTHORIZED")
    }

    fun render(message: String, error_code: String): ApiErrorDTO {
        return ApiErrorDTO(
            message = message, error_code = error_code
        )
    }
}