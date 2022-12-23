package com.gmcn.tokens.dao

import com.gmcn.tokens.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserMongoRepository : MongoRepository<User, String> {
    fun findByEmail(email: String): User?
}