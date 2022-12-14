package com.gmcn.finanaceplanneraccounts.datasource

import com.gmcn.finanaceplanneraccounts.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserDataSource : MongoRepository<User, String> {
    fun findByEmail(email: String): User?
}