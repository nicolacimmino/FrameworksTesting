package com.cimminonicola.finanaceplanneraccounts.controllers

import com.cimminonicola.finanaceplanneraccounts.entities.Account
import com.cimminonicola.finanaceplanneraccounts.entities.AccountsRepository
import com.cimminonicola.finanaceplanneraccounts.errors.InputInvalidApiException
import com.cimminonicola.finanaceplanneraccounts.errors.ResourceNotFoundApiException
import org.springframework.web.bind.annotation.*

@RestController
class AccountsController(
    private val accountsRepository: AccountsRepository
) {

    @GetMapping("/api/accounts")
    fun getAllAccounts(): List<Account> {
        return this.accountsRepository.findAll()
    }

    @PostMapping("/api/accounts")
    fun addAccount(@RequestBody account: Account): Account {
        if (this.accountsRepository.existsByName(account.name)) {
            throw InputInvalidApiException("Account exists")
        }

        this.accountsRepository.save(account)

        return account
    }

    @DeleteMapping("/api/accounts/{id}")
    fun deleteAccount(@PathVariable id: String) {
        if (!this.accountsRepository.existsById(id)) {
            throw ResourceNotFoundApiException("Account doesn't exist")
        }

        this.accountsRepository.deleteById(id)
    }
}