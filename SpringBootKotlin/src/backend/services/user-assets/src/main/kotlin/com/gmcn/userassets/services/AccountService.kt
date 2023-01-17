package com.gmcn.userassets.services

import com.gmcn.userassets.daos.AccountDAO
import com.gmcn.userassets.errors.InputInvalidApiException
import com.gmcn.userassets.errors.ResourceNotFoundApiException
import com.gmcn.userassets.models.Account
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountDAO: AccountDAO
) {

    fun getUserAccount(id: String): Account {
        return accountDAO.findOrNull(id) ?: throw ResourceNotFoundApiException()
    }

    fun getAllUserAccounts(userId: String): List<Account> {
        return accountDAO.findByUserId(userId)
    }

    fun addAccount(name: String, currency: String, userId: String): Account {
        if (accountDAO
                .findByUserId(userId)
                .any { it.name == name }
        ) {
            throw InputInvalidApiException("Duplicate account name")
        }

        return accountDAO.save(
            Account(
                name,
                currency,
                userId
            )
        )
    }

    fun deleteAccount(accountId: String) {
        accountDAO.delete(accountId)
    }
}