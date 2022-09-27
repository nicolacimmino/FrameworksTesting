package com.cimminonicola.finanaceplanneraccounts.errors

import org.springframework.http.HttpStatus

class InputInvalidApiException(message: String = "Input Invalid") : ApiException(message) {
    init {
        this.errorCode = "ERROR_INPUT_INVALID"
        this.httpStatus = HttpStatus.BAD_REQUEST
    }
}