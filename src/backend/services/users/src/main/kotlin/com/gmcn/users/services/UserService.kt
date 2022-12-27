package com.gmcn.users.services

import com.gmcn.users.daos.UserDAO
import com.gmcn.users.errors.InputInvalidApiException
import com.gmcn.users.errors.ResourceNotFoundApiException
import com.gmcn.users.models.User
import com.gmcn.users.remoteservices.TokensService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDAO: UserDAO
) {
    @Autowired
    lateinit var tokensService: TokensService

    fun getUser(id: String): User {
        return userDAO.findOrNull(id) ?: throw ResourceNotFoundApiException()
    }

    fun createUser(
        email: String, name: String, password: String
    ): User {
        if (userDAO.findByEmailOrNull(email) != null) {
            throw InputInvalidApiException("duplicate email")
        }

        val user = userDAO.save(User(name, email))

        tokensService.notifyNewUserCredentials(
            user.id, email, password
        )

        return user
    }

    fun updatePassword(
        userId: String, password: String
    ) {
        tokensService.updateUserPassword(
            userId, password
        )
    }
}