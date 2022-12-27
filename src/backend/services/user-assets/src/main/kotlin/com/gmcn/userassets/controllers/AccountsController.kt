package com.gmcn.userassets.controllers

import com.gmcn.userassets.dtos.CreateAccountDTO
import com.gmcn.userassets.dtos.CreateAccountResponseDTO
import com.gmcn.userassets.dtos.GetAccountResponseDTO
import com.gmcn.userassets.services.AccountService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api")
@CrossOrigin(origins = ["http://localhost:54845"])
class AccountsController(
    private val accountService: AccountService
) {

    @GetMapping("users/*/accounts")
    fun getAllAccounts(
        request: HttpServletRequest,
    ): List<GetAccountResponseDTO> {
        val accounts = accountService.getAllUserAccounts(request.getAttribute("auth.user_id").toString())

        return accounts.map { GetAccountResponseDTO(it.name, it.currency, it.balance, it.id) }
    }

    @GetMapping("users/*/accounts/{account_id}")
    fun getAccount(@PathVariable("account_id") accountId: String): GetAccountResponseDTO {
        val account = accountService.getUserAccount(accountId)

        return GetAccountResponseDTO(account.name, account.currency, account.balance, account.id)
    }

    @PostMapping("users/*/accounts")
    fun addAccount(
        request: HttpServletRequest,
        @RequestBody createAccountRequest: CreateAccountDTO
    ): CreateAccountResponseDTO {
        val account = accountService.addAccount(
            createAccountRequest.name,
            createAccountRequest.currency,
            request.getAttribute("auth.user_id").toString()
        )

        return CreateAccountResponseDTO(account.name, account.currency, account.balance, account.id)
    }

    @DeleteMapping("users/*/accounts/{account_id}")
    fun deleteAccount(@PathVariable("account_id") accountId: String) {
        accountService.deleteAccount(accountId)
    }
}