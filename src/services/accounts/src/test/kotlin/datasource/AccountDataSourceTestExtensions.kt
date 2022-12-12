package datasource

import com.gmcn.finanaceplanneraccounts.datasource.AccountDataSource
import com.gmcn.finanaceplanneraccounts.model.Account
import kotlin.random.Random


fun AccountDataSource.getTestAccountsForUser(userId: String, count: Int = 1): List<Account> {
    val currencies = listOf<String>("EUR", "GBP", "USD")
    val accounts = mutableListOf<Account>()

    for (ix in 0 until count) {
        accounts.add(Account("Test $count", currencies[Random.nextInt(currencies.size)], userId, 100.0f))
    }

    return accounts
}
