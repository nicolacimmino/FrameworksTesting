package com.cimminonicola.finanaceplanneraccounts.errors

import org.springframework.http.HttpStatus

class ResourceNotFoundApiException(message: String = "Not Found") : ApiException(message) {
    init {
        this.errorCode = "ERROR_NOT_FOUND"
        this.httpStatus = HttpStatus.NOT_FOUND
    }
}