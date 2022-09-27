package com.cimminonicola.finanaceplanneraccounts.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus
import java.time.LocalDateTime
import java.time.ZoneId


class ApiErrorDTO(
    var error: String,
    var error_code: String,
    @JsonIgnore
    var httpStatus: HttpStatus
) {


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd hh:mm:ss")
    var timestamp: LocalDateTime? = null

    init {
        this.timestamp = LocalDateTime.now(ZoneId.of("UTC"))
    }
}