package com.gmcn.users.errors

import org.springframework.http.HttpStatus

class UnauthorizedApiException(message: String = "Unauthorized") : ApiException(message) {
    init {
        errorCode = "ERROR_UNAUTHORIZED"
        httpStatus = HttpStatus.UNAUTHORIZED
    }
}