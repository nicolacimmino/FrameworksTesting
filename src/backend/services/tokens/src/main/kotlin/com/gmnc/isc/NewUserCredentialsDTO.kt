package com.gmnc.isc

import com.fasterxml.jackson.annotation.JsonProperty

data class NewUserCredentialsDTO(
    @JsonProperty("userId")
    var userId: String,
    @JsonProperty("email")
    var email: String,
    @JsonProperty("password")
    var password: String
)
