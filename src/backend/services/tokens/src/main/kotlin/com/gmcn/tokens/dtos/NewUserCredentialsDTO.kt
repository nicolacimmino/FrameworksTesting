package com.gmcn.tokens.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class NewUserCredentialsDTO(
    @JsonProperty("name")
    var name: String,
    @JsonProperty("password")
    var password: String
)
