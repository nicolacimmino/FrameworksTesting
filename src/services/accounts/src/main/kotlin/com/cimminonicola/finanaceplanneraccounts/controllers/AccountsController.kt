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

    @GetMapping("/api/users/{user_id}/accounts")
    fun getAllAccounts(): List<Account> {
        return this.accountsRepository.findAll()
    }

    @PostMapping("/api/users/{user_id}/accounts")
    fun addAccount(@RequestBody account: Account): Account {
        if (this.accountsRepository.existsByName(account.name)) {
            throw InputInvalidApiException("Account exists")
        }

        this.accountsRepository.save(account)

        return account
    }

    @DeleteMapping("/api/users/{user_id}/accounts/{account_id}")
    fun deleteAccount(@PathVariable account_id: String) {
        if (!this.accountsRepository.existsById(account_id)) {
            throw ResourceNotFoundApiException("Account doesn't exist")
        }

        this.accountsRepository.deleteById(account_id)
    }

    @DeleteMapping("/api/users/{user_id}/accounts/")
    fun deleteAccountByName(@RequestParam("name") name: String) {
        if (!this.accountsRepository.existsByName(name)) {
            throw ResourceNotFoundApiException("Account doesn't exist")
        }

        this.accountsRepository.deleteByName(name)
    }
}