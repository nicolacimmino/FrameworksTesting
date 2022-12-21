package com.gmcn.finanaceplanneraccounts.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Document(collection = "accounts")
data class Account(
    var name: String,
    var currency: String,
    @JsonIgnore
    var userId: String,
    var balance: Float = 0.0f
) : OwnedModel(userId) {
    @Id
    @GeneratedValue
    lateinit var id: String

    private var internalReference: String = UUID.randomUUID().toString()

    @JsonProperty
    fun setInternalReference(value: String?) {
        internalReference = value ?: UUID.randomUUID().toString()
    }
}