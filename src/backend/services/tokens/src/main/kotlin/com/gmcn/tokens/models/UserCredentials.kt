package com.gmcn.tokens.models

import jakarta.persistence.Column
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Document(collection = "user-credentials")
class UserCredentials {
    @Column
    var userId: String = ""

    @Column
    var email: String = ""

    @Column
    var password: String = ""
        set(value) {
            field = BCryptPasswordEncoder().encode(value)
        }

    fun isPasswordValid(passwordToValidate: String): Boolean {
        return BCryptPasswordEncoder().matches(passwordToValidate, password)
    }
}