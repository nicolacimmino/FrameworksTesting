package com.gmcn.userassets.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

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