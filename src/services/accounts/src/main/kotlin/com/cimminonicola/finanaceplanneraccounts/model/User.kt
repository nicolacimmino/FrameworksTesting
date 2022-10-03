package com.cimminonicola.finanaceplanneraccounts.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Document(collection = "users")
// @CompoundIndex(name = "age_idx", def = "{'lastName': 1, 'age': -1}")
class User {

// TODO: this is supposed to work, but the field gets serialized anyway and throws an error.
//    @Transient
//    private val passwordEncoder = BCryptPasswordEncoder()

    @Id
    @GeneratedValue
    lateinit var id: String

    @Column
    var name: String = ""

    @Column
    @Indexed(unique = true) // TODO: doesn't work!
    var email: String = ""

    @Column
    @JsonIgnore
    var password: String = ""
        get() = field
        set(value) {
            field = BCryptPasswordEncoder().encode(value)
        }

    fun isPasswordValid(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.password)
    }
}