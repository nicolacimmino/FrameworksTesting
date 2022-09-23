package com.cimminonicola.finanaceplanneraccounts.controllers

import com.cimminonicola.finanaceplanneraccounts.entities.Account
import com.cimminonicola.finanaceplanneraccounts.entities.AccountsRepository
import com.cimminonicola.finanaceplanneraccounts.errors.InputInvalidApiException
import com.cimminonicola.finanaceplanneraccounts.errors.ResourceNotFoundApiException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api")
class AccountsController(
    private val accountsRepository: AccountsRepository
) {

    @GetMapping("users/{user_id}/accounts")
    fun getAllAccounts(): List<Account> {
        return this.accountsRepository.findAll()
    }

    @PostMapping("users/{user_id}/accounts")
    fun addAccount(@RequestBody account: Account): Account {
        if (this.accountsRepository.existsByName(account.name)) {
            throw InputInvalidApiException("Account exists")
        }

        this.accountsRepository.save(account)

        return account
    }

    @DeleteMapping("users/{user_id}/accounts/{account_id}")
    fun deleteAccount(@PathVariable("account_id") accountId: String) {
        if (!this.accountsRepository.existsById(accountId)) {
            throw ResourceNotFoundApiException("Account doesn't exist")
        }

        this.accountsRepository.deleteById(accountId)
    }

    @DeleteMapping("users/{user_id}/accounts/")
    fun deleteAccountByName(@RequestParam("name", required = true) name: String) {

        if (name == "*") {
            return this.deleteAllAccounts()
        }

        if (!this.accountsRepository.existsByName(name)) {
            throw ResourceNotFoundApiException("Account doesn't exist")
        }

        this.accountsRepository.deleteByName(name)
    }

    private fun deleteAllAccounts() {
        val allAccounts = this.accountsRepository.findAll()

        allAccounts.forEach {
            this.accountsRepository.deleteById(it.id)
        }
    }
}