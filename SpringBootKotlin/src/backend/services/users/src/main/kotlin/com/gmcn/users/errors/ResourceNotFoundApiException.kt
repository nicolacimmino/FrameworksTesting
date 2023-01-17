package com.gmcn.users.errors

import org.springframework.http.HttpStatus

class ResourceNotFoundApiException(message: String = "Not Found") : ApiException(message) {
    init {
        errorCode = "ERROR_NOT_FOUND"
        httpStatus = HttpStatus.NOT_FOUND
    }
}