package com.gmcn.tokens.service

import com.gmcn.tokens.ApplicationStatus
import com.gmcn.tokens.dao.AccountDAO
import com.gmcn.tokens.errors.InputInvalidApiException
import com.gmcn.tokens.errors.ResourceNotFoundApiException
import com.gmcn.tokens.model.Account
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountDAO: AccountDAO,
    private var applicationStatus: ApplicationStatus,
) {

    fun getUserAccount(id: String): Account {
        return accountDAO.findOrNull(id) ?: throw ResourceNotFoundApiException()
    }

    fun getAllUserAccounts(id: String): List<Account> {
        return accountDAO.findByUserId(applicationStatus.authorizedUserId)
    }

    fun addAccount(name: String, currency: String): Account {
        if (accountDAO
                .findByUserId(applicationStatus.authorizedUserId)
                .any { it.name == name }
        ) {
            throw InputInvalidApiException("Duplicate account name")
        }

        return accountDAO.save(
            Account(
                name,
                currency,
                applicationStatus.authorizedUserId
            )
        )
    }

    fun deleteAccount(accountId: String) {
        accountDAO.delete(accountId)
    }
}