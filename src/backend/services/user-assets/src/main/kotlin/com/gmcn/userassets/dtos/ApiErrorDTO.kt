package com.gmcn.userassets.dtos

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class ApiErrorDTO(
    var error: String,
    var error_code: String,
    @JsonIgnore
    var httpStatus: HttpStatus?,
    var timestamp: String = LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_DATE_TIME)
)