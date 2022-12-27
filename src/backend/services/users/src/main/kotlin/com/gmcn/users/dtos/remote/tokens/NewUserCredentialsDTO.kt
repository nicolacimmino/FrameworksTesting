package com.gmcn.users.dtos.remote.tokens

import com.fasterxml.jackson.annotation.JsonProperty

data class NewUserCredentialsDTO(
    @JsonProperty("userId")
    var userId: String,
    @JsonProperty("email")
    var email: String,
    @JsonProperty("password")
    var password: String
)
