package com.gmcn.finanaceplanneraccounts.dao

import com.gmcn.finanaceplanneraccounts.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserMongoRepository : MongoRepository<User, String> {
    fun findByEmail(email: String): User?
}