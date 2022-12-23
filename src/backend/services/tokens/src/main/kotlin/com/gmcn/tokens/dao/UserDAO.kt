package com.gmcn.tokens.dao

import com.gmcn.tokens.model.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserDAO(
    private val usersRepository: UserMongoRepository
) {
    fun findOrNull(userId: String): User? {
        return usersRepository.findByIdOrNull(userId)
    }

    fun delete(userId: String) {
        return usersRepository.deleteById(userId)
    }

    fun findByEmailOrNull(email: String): User? {
        return usersRepository.findByEmail(email)
    }

    fun save(user: User): User {
        return usersRepository.save(user)
    }
}