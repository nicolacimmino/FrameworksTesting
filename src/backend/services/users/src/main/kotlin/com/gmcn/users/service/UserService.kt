package com.gmcn.users.service

import com.gmcn.users.dao.UserDAO
import com.gmcn.users.errors.ResourceNotFoundApiException
import com.gmcn.users.model.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDAO: UserDAO
) {

    fun getUser(id: String): User {
        return userDAO.findOrNull(id) ?: throw ResourceNotFoundApiException()
    }
}