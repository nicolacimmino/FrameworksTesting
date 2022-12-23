package com.gmcn.tokens.service

import com.gmcn.tokens.dao.UserDAO
import com.gmcn.tokens.errors.ResourceNotFoundApiException
import com.gmcn.tokens.model.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDAO: UserDAO
) {

    fun getUser(id: String): User {
        return userDAO.findOrNull(id) ?: throw ResourceNotFoundApiException()
    }
}