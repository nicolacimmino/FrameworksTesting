package com.cimminonicola.finanaceplanneraccounts.errors

import org.springframework.http.HttpStatus

class InternalErrorApiException(message: String = "Internal Error") : ApiException(message) {
    init {
        errorCode = "ERROR_INTERNAL"
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    }
}