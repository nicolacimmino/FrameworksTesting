package com.gmcn.finanaceplanneraccounts.datasource

import com.gmcn.finanaceplanneraccounts.model.Account
import org.springframework.data.mongodb.repository.MongoRepository

interface AccountDataSource : MongoRepository<Account, String> {
    fun findAllByUserId(userId: String): List<Account>
    fun deleteByUserId(userId: String)
    fun existsByName(name: String): Boolean
    fun deleteByName(name: String)

    companion object {

    }
}