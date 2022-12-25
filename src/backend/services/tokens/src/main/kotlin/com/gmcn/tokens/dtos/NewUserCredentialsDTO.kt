// TODO: FIX! THis should be in the tokens.dtos package. See NewUserCredentialsReceiver for issues with class mapping
package com.gmcn.users.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class NewUserCredentialsDTO(
    @JsonProperty("userId")
    var userId: String,
    @JsonProperty("email")
    var email: String,
    @JsonProperty("password")
    var password: String
)
