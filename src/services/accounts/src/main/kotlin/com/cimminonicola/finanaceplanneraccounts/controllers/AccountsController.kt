package com.cimminonicola.finanaceplanneraccounts.controllers

import com.cimminonicola.finanaceplanneraccounts.ApplicationStatus
import com.cimminonicola.finanaceplanneraccounts.datasource.AccountDataSource
import com.cimminonicola.finanaceplanneraccounts.dtos.CreateAccountDTO
import com.cimminonicola.finanaceplanneraccounts.errors.InputInvalidApiException
import com.cimminonicola.finanaceplanneraccounts.errors.ResourceNotFoundApiException
import com.cimminonicola.finanaceplanneraccounts.model.Account
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api")
@CrossOrigin(origins = ["http://localhost:59729"])
class AccountsController(
    private val accountDataSource: AccountDataSource
) {
    @Autowired
    lateinit var applicationStatus: ApplicationStatus

    @GetMapping("users/*/accounts")
    fun getAllAccounts(): List<Account> {
        return accountDataSource.findAllByUserId(applicationStatus.authorizedUserId)
    }

    @GetMapping("users/*/accounts/{account_id}")
    fun getAccount(@PathVariable("account_id") accountId: String) : Account {

        return accountDataSource.findByIdOrNull(accountId) ?:
           throw ResourceNotFoundApiException("Account doesn't exist")
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