package com.gmcn.finanaceplanneraccounts.service

import com.gmcn.finanaceplanneraccounts.dao.UserDAO
import com.gmcn.finanaceplanneraccounts.errors.ResourceNotFoundApiException
import com.gmcn.finanaceplanneraccounts.model.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDAO: UserDAO
) {

    fun getUser(id: String): User {
        return userDAO.findOrNull(id) ?: throw ResourceNotFoundApiException()
    }
}