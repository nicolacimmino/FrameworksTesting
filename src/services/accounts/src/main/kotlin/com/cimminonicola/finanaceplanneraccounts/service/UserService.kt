package com.cimminonicola.finanaceplanneraccounts.service

import com.cimminonicola.finanaceplanneraccounts.datasource.UserDataSource
import com.cimminonicola.finanaceplanneraccounts.errors.ResourceNotFoundApiException
import com.cimminonicola.finanaceplanneraccounts.model.User
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