package com.cimminonicola.finanaceplanneraccounts.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

// TODO: define an errorDTO and change render to createErrorDTO

@ControllerAdvice
class ApiErrorHandler {
    @ResponseBody
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun defaultHandler(exception: Exception): ApiError {
        return this.render(exception.message ?: "Internal Error", "INTERNAL_ERROR")
    }

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundApiException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun resourceNotFoundHandler(exception: ResourceNotFoundApiException): ApiError {
        return this.render(exception.message ?: "Not Found", "NOT_FOUND")
    }

    @ResponseBody
    @ExceptionHandler(InputInvalidApiException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun inputInvalidHandler(exception: InputInvalidApiException): ApiError {
        return this.render(exception.message ?: "Input Invalid", "INPUT_INVALID")
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun missingParameterHandler(exception: MissingServletRequestParameterException): ApiError {
        return this.render(exception.message, "MISSING_PARAMETER")
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedApiException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun missingParameterHandler(exception: UnauthorizedApiException): ApiError {
        return this.render(exception.message ?: "Unauthorized", "UNAUTHORIZED")
    }

    fun render(message: String, error_code: String): ApiError {
        return ApiError(
            message = message,
            error_code = error_code
        )
    }
}