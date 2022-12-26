package com.gmcn.userassets.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class ValidateTokenDTO(
    @JsonProperty("token")
    var token: String,
)
