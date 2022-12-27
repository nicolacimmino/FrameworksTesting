package com.gmcn.tokens.services

import com.gmcn.tokens.daos.UserCredentialsDAO
import com.gmcn.tokens.errors.ResourceNotFoundApiException
import com.gmcn.tokens.models.UserCredentials
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserCredentialsService() {
    @Autowired
    private lateinit var userCredentialsDAO: UserCredentialsDAO

    fun updateUserCredentials(userId: String, email: String, password: String) {
        var userCredentials = UserCredentials()
        userCredentials.userId = userId
        userCredentials.email = email
        userCredentials.password = password

        userCredentialsDAO.save(userCredentials)
    }

    fun updateUserPassword(userId: String, password: String) {
        var userCredentials = userCredentialsDAO.findByIdOrNull(userId) ?: throw ResourceNotFoundApiException()

        userCredentials.password = password

        userCredentialsDAO.save(userCredentials)
    }

    fun isPasswordValid(userCredentials: UserCredentials, passwordToValidate: String): Boolean {
        return BCryptPasswordEncoder().matches(passwordToValidate, userCredentials.password)
    }
}
