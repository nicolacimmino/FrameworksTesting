package com.gmcn.users.daos

import com.gmcn.users.models.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserMongoRepository : MongoRepository<User, String> {
    fun findByEmail(email: String): User?
}