package com.gmcn.finanaceplanneraccounts.service

import com.gmcn.finanaceplanneraccounts.datasource.UserDataSource
import com.gmcn.finanaceplanneraccounts.errors.ResourceNotFoundApiException
import com.gmcn.finanaceplanneraccounts.model.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDataSource: UserDataSource
) {

    fun getUser(id: String): User {
        return userDataSource.findByIdOrNull(id) ?: throw ResourceNotFoundApiException()
    }
}