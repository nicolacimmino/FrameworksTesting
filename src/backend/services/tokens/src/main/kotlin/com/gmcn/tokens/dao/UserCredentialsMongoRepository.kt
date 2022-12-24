package com.gmcn.tokens.dao

import com.gmcn.tokens.model.UserCredentials
import org.springframework.data.mongodb.repository.MongoRepository

interface UserCredentialsMongoRepository : MongoRepository<UserCredentials, String> {
    fun findByEmail(email: String): UserCredentials?
}