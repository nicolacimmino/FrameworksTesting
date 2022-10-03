package com.cimminonicola.finanaceplanneraccounts.controllers

import com.cimminonicola.finanaceplanneraccounts.datasource.AccountDataSource
import com.cimminonicola.finanaceplanneraccounts.errors.InputInvalidApiException
import com.cimminonicola.finanaceplanneraccounts.errors.ResourceNotFoundApiException
import com.cimminonicola.finanaceplanneraccounts.model.Account
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api")
class AccountsController(
    private val accountDataSource: AccountDataSource
) {

    @GetMapping("users/{user_id}/accounts")
    fun getAllAccounts(@PathVariable("user_id") userId: String): List<Account> {
        return this.accountDataSource.findAllByUserId(userId)
    }

    // TODO: define a createAccountDTO and use instead so not all all fields can be forced by the request
    @PostMapping("users/{user_id}/accounts")
    fun addAccount(@PathVariable("user_id") userId: String, @RequestBody account: Account): Account {
        if (this.accountDataSource
                .findAllByUserId(userId)
                .any { it.name == account.name }
        ) {
            throw InputInvalidApiException("Account exists")
        }

        account.userId = userId

        this.accountDataSource.save(account)

        return account
    }

    @DeleteMapping("users/{user_id}/accounts/{account_id}")
    fun deleteAccount(@PathVariable("account_id") accountId: String) {
        if (!this.accountDataSource.existsById(accountId)) {
            throw ResourceNotFoundApiException("Account doesn't exist")
        }

        this.accountDataSource.deleteById(accountId)
    }

    @DeleteMapping("users/{user_id}/accounts/")
    fun deleteAccountByName(@RequestParam("name", required = true) name: String) {

        if (name == "*") {
            return this.deleteAllAccounts()
        }

        if (!this.accountDataSource.existsByName(name)) {
            throw ResourceNotFoundApiException("Account doesn't exist")
        }

        this.accountDataSource.deleteByName(name)
    }

    private fun deleteAllAccounts() {
        val allAccounts = this.accountDataSource.findAll()

        allAccounts.forEach {
            this.accountDataSource.deleteById(it.id)
        }
    }
}