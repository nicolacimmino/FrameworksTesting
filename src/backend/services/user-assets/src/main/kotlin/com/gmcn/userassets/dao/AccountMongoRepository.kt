package com.gmcn.userassets.dao

import com.gmcn.userassets.model.Account
import org.springframework.data.mongodb.repository.MongoRepository

interface AccountMongoRepository : MongoRepository<Account, String> {
    fun findAllByUserId(userId: String): List<Account>

    companion object {

    }
}