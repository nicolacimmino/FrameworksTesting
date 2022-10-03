package com.cimminonicola.finanaceplanneraccounts.datasource

import com.cimminonicola.finanaceplanneraccounts.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserDataSource : MongoRepository<User, String> {
    fun findByEmail(email: String): User?
}