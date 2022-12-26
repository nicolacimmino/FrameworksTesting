package com.gmnc.isc

import com.fasterxml.jackson.annotation.JsonProperty

data class ValidateTokenDTO(
    @JsonProperty("token")
    var token: String,
)
