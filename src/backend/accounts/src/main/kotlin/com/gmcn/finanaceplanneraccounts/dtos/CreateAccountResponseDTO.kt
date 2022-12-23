package com.gmcn.finanaceplanneraccounts.dtos

data class CreateAccountResponseDTO(
    var name: String,
    var currency: String,
    var balance: Float,
    var id: String
)