package com.cimminonicola.finanaceplanneraccounts.entities

import org.springframework.data.mongodb.repository.MongoRepository

interface AccountsRepository : MongoRepository<Account, String> {
    fun findByCurrency(currency: String): Account
    fun existsByName(name: String): Boolean
}