package com.gmcn.userassets.services

import com.gmcn.userassets.ApplicationStatus
import com.gmcn.userassets.daos.AccountDAO
import com.gmcn.userassets.errors.InputInvalidApiException
import com.gmcn.userassets.errors.ResourceNotFoundApiException
import com.gmcn.userassets.models.Account
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