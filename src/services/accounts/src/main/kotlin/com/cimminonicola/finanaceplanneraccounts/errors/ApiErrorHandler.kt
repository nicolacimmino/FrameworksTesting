package com.cimminonicola.finanaceplanneraccounts.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ApiExceptionHandler {

    fun render(message: String, error_code: String): ApiError {
        return ApiError(
            message = message,
            error_code = error_code
        )
    }

    @ResponseBody
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun defaultHandler(exception: Exception): ApiError {
        return this.render(exception.message ?: "", "INTERNAL_ERROR")
    }

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundApiException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun resourceNotFoundHandler(exception: ResourceNotFoundApiException): ApiError {
        return this.render(exception.message ?: "", "NOT_FOUND")
    }

    @ResponseBody
    @ExceptionHandler(InputInvalidApiException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun inputInvalidHandler(exception: InputInvalidApiException): ApiError {
        return this.render(exception.message ?: "", "INPUT_INVALID")
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun missingParameterHandler(exception: MissingServletRequestParameterException): ApiError {
        return this.render(exception.message, "MISSING_PARAMETER2")
    }
}