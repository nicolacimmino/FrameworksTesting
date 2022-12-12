package com.gmcn.finanaceplanneraccounts.dtos

data class CreateTokenResponseDTO(
    var token: String,
    var ttl: Int,
    var user_id: String
)