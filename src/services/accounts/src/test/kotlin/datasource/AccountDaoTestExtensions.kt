package datasource

import com.gmcn.finanaceplanneraccounts.dao.AccountDao
import com.gmcn.finanaceplanneraccounts.model.Account
import kotlin.random.Random

val AccountDao.Companion.userAccounts by lazy { mutableMapOf<String, MutableMap<String, Account>>() }

fun AccountDao.Companion.getTestAccountsForUser(userId: String, count: Int = 1): MutableMap<String, Account> {
    if (userAccounts.containsKey(userId)) {
        return userAccounts[userId]!!
    }

    val currencies = listOf("EUR", "GBP", "USD")
    val accounts = mutableMapOf<String, Account>()

    for (ix in 0 until count) {
        val account = Account("Test $count", currencies[Random.nextInt(currencies.size)], userId, 100.0f)
        account.id = account.name.hashCode().toString()

        accounts[account.id] = account
    }

    userAccounts[userId] = accounts

    return accounts
}

fun AccountDao.Companion.getTestAccount(userId: String, accountId: String): Account? {
    if (userAccounts.containsKey(userId) && userAccounts[userId]!!.containsKey(accountId)) {
        return userAccounts[userId]!![accountId]
    }

    return null
}