package com.gmcn.users.model

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
class User {
    @Id
    @GeneratedValue
    lateinit var id: String

    @Column
    var name: String = ""

    @Column
    @Indexed(unique = true)
    var email: String = ""
}