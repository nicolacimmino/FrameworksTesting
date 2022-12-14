package com.gmcn.finanaceplanneraccounts.dao

import com.gmcn.finanaceplanneraccounts.model.Account

interface AccountDao : EntityDao<Account, String> {
    fun findAllByUserId(userId: String): List<Account>
    fun deleteByUserId(userId: String)
    fun existsByName(name: String): Boolean
    fun deleteByName(name: String)

    // TODO: This is to support tests and add extensions that allow to store mocked data. What is a better way?
    companion object {

    }
}