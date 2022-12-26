package com.gmcn.tokens.daos

import com.gmcn.tokens.models.UserCredentials
import org.springframework.stereotype.Component

@Component
class UserCredentialsDAO(
    private val userCredentialsRepository: UserCredentialsMongoRepository
) {
    fun findByEmailOrNull(email: String): UserCredentials? {
        return userCredentialsRepository.findByEmail(email)
    }

    fun save(userCredentials: UserCredentials): UserCredentials {
        return userCredentialsRepository.save(userCredentials)
    }
}