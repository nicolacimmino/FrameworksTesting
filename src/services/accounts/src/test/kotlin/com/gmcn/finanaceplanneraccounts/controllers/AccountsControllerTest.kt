package com.gmcn.finanaceplanneraccounts.controllers

import com.gmcn.finanaceplanneraccounts.ApplicationStatus
import com.gmcn.finanaceplanneraccounts.datasource.AccountDataSource
import com.gmcn.finanaceplanneraccounts.errors.ResourceNotFoundApiException
import datasource.getTestAccount
import datasource.getTestAccountsForUser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import kotlin.test.assertEquals

private const val testUserAId = "c4ca4238a0b923820dcc509a6f75849b"
private const val testUserBId = "c81e728d9d4c2f636f067f89cc14862c"

@ExtendWith(MockitoExtension::class)
class AccountsControllerTest {

    @InjectMocks
    private lateinit var accountsController: AccountsController

    @Mock
    lateinit var accountDataSource: AccountDataSource

    @Mock
    lateinit var applicationStatus: ApplicationStatus


    @BeforeEach
    fun setUp() {
        accountsController = AccountsController(accountDataSource, applicationStatus)
    }

    @Test
    fun getAllAccounts() {
        Mockito.`when`(applicationStatus.authorizedUserId).thenReturn(testUserAId)

        Mockito.`when`(accountDataSource.findAllByUserId(testUserAId)).thenReturn(
            AccountDataSource.getTestAccountsForUser(testUserAId).map { it.value }
        )

        val accounts = accountsController.getAllAccounts()

        assertEquals(AccountDataSource.getTestAccountsForUser(testUserAId).size, accounts.size)
        accounts.forEach { assertEquals(testUserAId, it.userId) }
    }

    @Test
    fun getAccount() {
        var testAccount = AccountDataSource.getTestAccountsForUser(testUserAId).entries.first().value

        Mockito.`when`(applicationStatus.authorizedUserId).thenReturn(testUserAId)

        Mockito.`when`(accountDataSource.findById(testAccount.id)).thenReturn(
            Optional.ofNullable(AccountDataSource.getTestAccount(testUserAId, testAccount.id))
        )

        val returnedAccount = accountsController.getAccount(testAccount.id)

        assertAll("account", {
            assertEquals(testAccount.id, returnedAccount.id)
            assertEquals(testAccount.name, returnedAccount.name)
            assertEquals(testAccount.currency, returnedAccount.currency)
            assertEquals(testAccount.balance, returnedAccount.balance)
        })
    }

    @Test()
    fun getSomeoneElseAccountFails() {
        var testAccount = AccountDataSource.getTestAccountsForUser(testUserAId).entries.first().value

        Mockito.`when`(applicationStatus.authorizedUserId).thenReturn(testUserBId)

        Mockito.`when`(accountDataSource.findById(testAccount.id)).thenReturn(
            Optional.ofNullable(AccountDataSource.getTestAccount(testUserAId, testAccount.id))
        )

        assertThrows<ResourceNotFoundApiException> {
            accountsController.getAccount(testAccount.id)
        }
    }
//
//    @Test
//    fun addAccount() {
//    }
//
//    @Test
//    fun deleteAccount() {
//    }
//
//    @Test
//    fun deleteAccountByName() {
//    }
}