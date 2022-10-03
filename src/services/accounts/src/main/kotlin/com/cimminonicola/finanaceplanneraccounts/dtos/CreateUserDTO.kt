package com.cimminonicola.finanaceplanneraccounts.dtos

data class CreateUserDTO(
    var name: String,
    var email: String,
    var password: String
)