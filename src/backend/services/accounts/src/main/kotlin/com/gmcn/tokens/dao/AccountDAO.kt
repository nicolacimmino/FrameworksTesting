package com.gmcn.tokens.dao

import com.gmcn.tokens.model.Account
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class AccountDAO(
    private val accountsRepository: AccountMongoRepository
) {
    fun findOrNull(accountId: String): Account? {
        return accountsRepository.findByIdOrNull(accountId)
    }

    fun findByUserId(userId: String): List<Account> {
        return accountsRepository.findAllByUserId(userId)
    }

    fun delete(accountId: String) {
        return accountsRepository.deleteById(accountId)
    }

    fun save(account: Account): Account {
        return accountsRepository.save(account)
    }
}