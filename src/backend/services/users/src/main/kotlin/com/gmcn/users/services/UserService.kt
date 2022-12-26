package com.gmcn.users.services

import com.gmcn.users.daos.UserDAO
import com.gmcn.users.errors.ResourceNotFoundApiException
import com.gmcn.users.models.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDAO: UserDAO
) {

    fun getUser(id: String): User {
        return userDAO.findOrNull(id) ?: throw ResourceNotFoundApiException()
    }
}