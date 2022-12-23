package com.gmcn.tokens.controllers

import com.gmcn.tokens.ApplicationStatus
import com.gmcn.tokens.dtos.CreateAccountDTO
import com.gmcn.tokens.dtos.CreateAccountResponseDTO
import com.gmcn.tokens.dtos.GetAccountResponseDTO
import com.gmcn.tokens.service.AccountService
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
    fun addAccount(@RequestBody createAccountRequest: CreateAccountDTO): CreateAccountResponseDTO {
        val account = accountService.addAccount(createAccountRequest.name, createAccountRequest.currency)

        return CreateAccountResponseDTO(account.name, account.currency, account.balance, account.id)
    }

    @DeleteMapping("users/*/accounts/{account_id}")
    fun deleteAccount(@PathVariable("account_id") accountId: String) {
        accountService.deleteAccount(accountId)
    }
}