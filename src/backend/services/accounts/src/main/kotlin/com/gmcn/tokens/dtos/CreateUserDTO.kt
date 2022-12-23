package com.gmcn.tokens.dtos

data class CreateUserDTO(
    var name: String,
    var email: String,
    var password: String
)