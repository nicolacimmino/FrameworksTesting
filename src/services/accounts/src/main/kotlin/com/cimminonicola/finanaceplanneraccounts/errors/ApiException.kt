package com.cimminonicola.finanaceplanneraccounts.errors

import com.cimminonicola.finanaceplanneraccounts.dtos.ApiErrorDTO
import org.springframework.http.HttpStatus

abstract class ApiException(val error: String = "") : Exception(error) {

    var errorCode: String = "ERROR_INTERNAL"

    var httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR

    fun toDTO(): ApiErrorDTO {
        return ApiErrorDTO(
            error = this.error,
            error_code = this.errorCode,
            httpStatus = this.httpStatus
        )
    }
}