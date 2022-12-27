package com.gmcn.users.dtos.remote.tokens

import com.fasterxml.jackson.annotation.JsonProperty

data class ValidateTokenDTO(
    @JsonProperty("token")
    var token: String,
)
