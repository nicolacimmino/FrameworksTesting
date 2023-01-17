package com.gmcn.userassets.daos

import com.gmcn.userassets.models.Account
import org.springframework.data.mongodb.repository.MongoRepository

interface AccountMongoRepository : MongoRepository<Account, String> {
    fun findAllByUserId(userId: String): List<Account>

    companion object {

    }
}