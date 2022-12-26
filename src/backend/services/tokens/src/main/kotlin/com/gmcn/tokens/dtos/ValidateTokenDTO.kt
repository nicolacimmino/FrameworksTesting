package com.gmcn.tokens.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class ValidateTokenDTO(
    @JsonProperty("token")
    var token: String,
)
