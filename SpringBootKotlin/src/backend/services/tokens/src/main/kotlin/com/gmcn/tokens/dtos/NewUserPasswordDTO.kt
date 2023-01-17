package com.gmcn.tokens.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class NewUserPasswordDTO(
    @JsonProperty("userId")
    var userId: String,
    @JsonProperty("password")
    var password: String
)
