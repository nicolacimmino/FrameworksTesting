package com.gmcn.finanaceplanneraccounts.service

import com.gmcn.finanaceplanneraccounts.ApplicationStatus
import com.gmcn.finanaceplanneraccounts.datasource.AccountDataSource
import com.gmcn.finanaceplanneraccounts.errors.InputInvalidApiException
import com.gmcn.finanaceplanneraccounts.errors.ResourceNotFoundApiException
import com.gmcn.finanaceplanneraccounts.model.Account
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountDataSource: AccountDataSource,
    private var applicationStatus: ApplicationStatus,
) {

    fun getUserAccount(id: String): Account {
        val account = findUserAccount(id) ?: throw ResourceNotFoundApiException()

        if (!isOwnedByUser(account)) {
            throw ResourceNotFoundApiException()
        }

        return account
    }

    fun getAllUserAccounts(id: String): List<Account> {
        return accountDataSource.findAllByUserId(applicationStatus.authorizedUserId)
    }

    fun addAccount(name: String, currency: String): Account {
        if (accountDataSource
                .findAllByUserId(applicationStatus.authorizedUserId)
                .any { it.name == name }
        ) {
            throw InputInvalidApiException("Duplicate account name")
        }

        return accountDataSource.save(
            Account(
                name,
                currency,
                applicationStatus.authorizedUserId
            )
        )
    }

    fun deleteAccount(accountId: String) {
        findUserAccount(accountId);

        accountDataSource.deleteById(accountId)
    }

    private fun findUserAccount(id: String): Account? {
        return accountDataSource.findByIdOrNull(id)
    }

    private fun isOwnedByUser(account: Account): Boolean {
        return (account.userId == applicationStatus.authorizedUserId)
    }
}