package com.cimminonicola.finanaceplanneraccounts.entities

import org.springframework.data.mongodb.repository.MongoRepository

interface UsersRepository : MongoRepository<User, String> {
    fun findByEmail(email: String): User?
}