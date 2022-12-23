package com.gmcn.finanaceplanneraccounts.dtos

data class GetAccountResponseDTO(
    var name: String,
    var currency: String,
    var balance: Float,
    var id: String
)