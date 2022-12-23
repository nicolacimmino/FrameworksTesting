package com.gmcn.tokens.dao

import com.gmcn.tokens.model.Account
import org.springframework.data.mongodb.repository.MongoRepository

interface AccountMongoRepository : MongoRepository<Account, String> {
    fun findAllByUserId(userId: String): List<Account>

    companion object {

    }
}