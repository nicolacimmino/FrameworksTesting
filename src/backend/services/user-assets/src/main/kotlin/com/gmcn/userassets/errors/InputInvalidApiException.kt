package com.gmcn.userassets.errors

import org.springframework.http.HttpStatus

class InputInvalidApiException(message: String = "Input Invalid") : ApiException(message) {
    init {
        errorCode = "ERROR_INPUT_INVALID"
        httpStatus = HttpStatus.BAD_REQUEST
    }
}