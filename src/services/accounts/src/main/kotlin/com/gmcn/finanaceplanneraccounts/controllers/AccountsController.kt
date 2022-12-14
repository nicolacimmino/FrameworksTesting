package com.gmcn.finanaceplanneraccounts.controllers

import com.gmcn.finanaceplanneraccounts.ApplicationStatus
import com.gmcn.finanaceplanneraccounts.datasource.AccountDataSource
import com.gmcn.finanaceplanneraccounts.dtos.CreateAccountDTO
import com.gmcn.finanaceplanneraccounts.errors.InputInvalidApiException
import com.gmcn.finanaceplanneraccounts.errors.ResourceNotFoundApiException
import com.gmcn.finanaceplanneraccounts.model.Account
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api")
@CrossOrigin(origins = ["http://localhost:54845"])
class AccountsController(
    private val accountDataSource: AccountDataSource,
    private var applicationStatus: ApplicationStatus
) {

    @GetMapping("users/*/accounts")
    fun getAllAccounts(): List<Account> {
        return accountDataSource.findAllByUserId(applicationStatus.authorizedUserId)
    }

    @GetMapping("users/*/accounts/{account_id}")
    fun getAccount(@PathVariable("account_id") accountId: String): Account {

        val account = accountDataSource.findByIdOrNull(accountId)
            ?: throw ResourceNotFoundApiException("Account doesn't exist")

        if (account.userId != applicationStatus.authorizedUserId) {
            throw ResourceNotFoundApiException("Account doesn't exist")
        }

        return account
    }

    @PostMapping("users/*/accounts")
    fun addAccount(@RequestBody createAccountRequest: CreateAccountDTO): Account {
        if (accountDataSource
                .findAllByUserId(applicationStatus.authorizedUserId)
                .any { it.name == createAccountRequest.name }
        ) {
            throw InputInvalidApiException("Duplicate account name")
        }

        val account = Account(
            createAccountRequest.name,
            createAccountRequest.currency,
            applicationStatus.authorizedUserId
        )

        return accountDataSource.save(account)
    }

    @DeleteMapping("users/*/accounts/{account_id}")
    fun deleteAccount(@PathVariable("account_id") accountId: String) {
        if (!accountDataSource
                .findAllByUserId(applicationStatus.authorizedUserId)
                .any { it.id == accountId }
        ) {
            throw ResourceNotFoundApiException("Account doesn't exist")
        }

        accountDataSource.deleteById(accountId)
    }

    @DeleteMapping("users/*/accounts/")
    fun deleteAccountByName(@RequestParam("name", required = true) name: String) {

        if (name == "*") {
            accountDataSource.deleteByUserId(applicationStatus.authorizedUserId)

            return
        }

        if (!accountDataSource.existsByName(name)) {
            throw ResourceNotFoundApiException("Account doesn't exist")
        }

        accountDataSource.deleteByName(name)
    }
}