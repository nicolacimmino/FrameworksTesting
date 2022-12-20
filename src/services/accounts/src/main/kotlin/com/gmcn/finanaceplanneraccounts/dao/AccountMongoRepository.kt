package com.gmcn.finanaceplanneraccounts.dao

import com.gmcn.finanaceplanneraccounts.model.Account
import org.springframework.data.mongodb.repository.MongoRepository

interface AccountMongoRepository : MongoRepository<Account, String> {
    fun findAllByUserId(userId: String): List<Account>

    companion object {

    }
}