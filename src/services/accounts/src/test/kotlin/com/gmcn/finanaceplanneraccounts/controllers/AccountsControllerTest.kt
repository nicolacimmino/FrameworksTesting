package com.gmcn.finanaceplanneraccounts.controllers

import com.gmcn.finanaceplanneraccounts.ApplicationStatus
import com.gmcn.finanaceplanneraccounts.datasource.AccountDataSource
import datasource.getTestAccountsForUser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals

private const val testUserAId = "c4ca4238a0b923820dcc509a6f75849b"

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

        Mockito.`when`(accountDataSource.findAllByUserId(testUserAId)).thenReturn(
            accountDataSource.getTestAccountsForUser(testUserAId)
        )
    }

    @Test
    fun getAllAccounts() {
        Mockito.`when`(applicationStatus.authorizedUserId).thenReturn(testUserAId)

        val accounts = accountsController.getAllAccounts()

        assertEquals(1, accounts.size)
    }

    @Test
    fun getAccount() {
    }

    @Test
    fun addAccount() {
    }

    @Test
    fun deleteAccount() {
    }

    @Test
    fun deleteAccountByName() {
    }
}