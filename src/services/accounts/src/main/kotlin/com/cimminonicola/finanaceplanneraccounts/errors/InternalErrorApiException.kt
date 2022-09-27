package com.cimminonicola.finanaceplanneraccounts.errors

import org.springframework.http.HttpStatus

class InternalErrorApiException(message: String = "Internal Error") : ApiException(message) {
    init {
        this.errorCode = "ERROR_INTERNAL"
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    }
}