package com.cimminonicola.finanaceplanneraccounts.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.time.ZoneId


class ApiErrorDTO(var message: String = "Error", var error_code: String = "ERROR") {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd hh:mm:ss")
    var timestamp: LocalDateTime? = null

    init {
        this.timestamp = LocalDateTime.now(ZoneId.of("UTC"))
    }
}