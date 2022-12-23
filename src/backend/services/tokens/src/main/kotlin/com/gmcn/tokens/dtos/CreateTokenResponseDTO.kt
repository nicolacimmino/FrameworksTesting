package com.gmcn.tokens.dtos

data class CreateTokenResponseDTO(
    var token: String,
    var ttl: Int,
    var user_id: String
)