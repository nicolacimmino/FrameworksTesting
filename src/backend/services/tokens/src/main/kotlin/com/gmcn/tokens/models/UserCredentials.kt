package com.gmcn.tokens.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

// Note: @Document is mongo specific as the JPA @Table annotation is not honoured by MongoRepository

@Entity
@Table(name = "user-credentials")
@Document(collection = "user-credentials")
class UserCredentials {
    @Id
    @Column(name = "userId", nullable = false)
    var userId: String = ""

    var email: String = ""

    var password: String = ""
        set(value) {
            field = BCryptPasswordEncoder().encode(value)
        }
}