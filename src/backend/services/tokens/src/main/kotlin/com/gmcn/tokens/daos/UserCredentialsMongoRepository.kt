package com.gmcn.tokens.daos

import com.gmcn.tokens.models.UserCredentials
import org.springframework.data.mongodb.repository.MongoRepository

interface UserCredentialsMongoRepository : MongoRepository<UserCredentials, String> {
    fun findByEmail(email: String): UserCredentials?
}