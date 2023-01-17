package com.gmcn.userassets.errors

import com.gmcn.userassets.dtos.ApiErrorDTO
import org.springframework.http.HttpStatus

abstract class ApiException(private val error: String = "error") : Exception(error) {

    var errorCode: String = "ERROR_INTERNAL"

    var httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR

    fun toDTO(): ApiErrorDTO {
        return ApiErrorDTO(error, errorCode, httpStatus)
    }
}