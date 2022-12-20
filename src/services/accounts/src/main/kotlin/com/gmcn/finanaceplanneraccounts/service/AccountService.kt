package com.gmcn.finanaceplanneraccounts.service

import com.gmcn.finanaceplanneraccounts.ApplicationStatus
import com.gmcn.finanaceplanneraccounts.dao.AccountDAO
import com.gmcn.finanaceplanneraccounts.errors.InputInvalidApiException
import com.gmcn.finanaceplanneraccounts.errors.ResourceNotFoundApiException
import com.gmcn.finanaceplanneraccounts.model.Account
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