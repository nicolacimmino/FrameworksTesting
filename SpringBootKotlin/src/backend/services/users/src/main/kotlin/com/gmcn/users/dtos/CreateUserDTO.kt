package com.gmcn.users.dtos

data class CreateUserDTO(
    var name: String,
    var email: String,
    var password: String
)