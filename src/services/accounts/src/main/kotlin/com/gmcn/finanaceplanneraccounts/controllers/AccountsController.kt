package com.gmcn.finanaceplanneraccounts.controllers

import com.gmcn.finanaceplanneraccounts.ApplicationStatus
import com.gmcn.finanaceplanneraccounts.dtos.CreateAccountDTO
import com.gmcn.finanaceplanneraccounts.dtos.GetAccountResponseDTO
import com.gmcn.finanaceplanneraccounts.model.Account
import com.gmcn.finanaceplanneraccounts.service.AccountService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api")
@CrossOrigin(origins = ["http://localhost:54845"])
class AccountsController(
    private var applicationStatus: ApplicationStatus,
    private val accountService: AccountService
) {

    @GetMapping("users/*/accounts")
    fun getAllAccounts(): List<GetAccountResponseDTO> {
        val accounts = accountService.getAllUserAccounts(applicationStatus.authorizedUserId)

        return accounts.map { GetAccountResponseDTO(it.name, it.currency, it.balance, it.id) }
    }

    @GetMapping("users/*/accounts/{account_id}")
    fun getAccount(@PathVariable("account_id") accountId: String): GetAccountResponseDTO {
        val account = accountService.getUserAccount(accountId)

        return GetAccountResponseDTO(account.name, account.currency, account.balance, account.id)
    }

    @PostMapping("users/*/accounts")
    fun addAccount(@RequestBody createAccountRequest: CreateAccountDTO): Account {
        return accountService.addAccount(createAccountRequest.name, createAccountRequest.currency);
    }

    @DeleteMapping("users/*/accounts/{account_id}")
    fun deleteAccount(@PathVariable("account_id") accountId: String) {
        accountService.deleteAccount(accountId)
    }
}