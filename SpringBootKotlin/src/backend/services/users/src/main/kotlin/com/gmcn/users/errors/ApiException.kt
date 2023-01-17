package com.gmcn.users.errors

import com.gmcn.users.dtos.ApiErrorDTO
import org.springframework.http.HttpStatus

// TODO: Consider: all general API errors could be a shared library

abstract class ApiException(private val error: String = "error") : Exception(error) {

    var errorCode: String = "ERROR_INTERNAL"

    var httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR

    fun toDTO(): ApiErrorDTO {
        return ApiErrorDTO(error, errorCode, httpStatus)
    }
}