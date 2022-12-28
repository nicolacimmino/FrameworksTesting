package com.gmcn.users.services

import com.gmcn.users.dtos.ApiErrorDTO
import com.gmcn.users.errors.InputInvalidApiException
import com.gmcn.users.errors.InternalErrorApiException
import com.gmcn.users.errors.ResourceNotFoundApiException
import com.gmcn.users.errors.UnauthorizedApiException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class ApiErrorHandlerService {
    @ResponseBody
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun defaultHandler(exception: Exception): ApiErrorDTO {
        return InternalErrorApiException().toDTO()
    }

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundApiException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun resourceNotFoundHandler(exception: ResourceNotFoundApiException): ApiErrorDTO {
        return exception.toDTO()
    }

    @ResponseBody
    @ExceptionHandler(InputInvalidApiException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun inputInvalidHandler(exception: InputInvalidApiException): ApiErrorDTO {
        return exception.toDTO()
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedApiException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun missingParameterHandler(exception: UnauthorizedApiException): ApiErrorDTO {
        return exception.toDTO()
    }
}