package com.cimminonicola.finanaceplanneraccounts.entities

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Document
class Account(
    var name: String,
    var currency: String,
    @Id @GeneratedValue var id: String? = null
) {
}