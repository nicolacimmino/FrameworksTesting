package com.cimminonicola.finanaceplanneraccounts.errors

import org.springframework.http.HttpStatus

class UnauthorizedApiException(message: String = "Unauthorized") : ApiException(message) {
    init {
        this.errorCode = "ERROR_UNAUTHORIZED"
        this.httpStatus = HttpStatus.UNAUTHORIZED
    }
}