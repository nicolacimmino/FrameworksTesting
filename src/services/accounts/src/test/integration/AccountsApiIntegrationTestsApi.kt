package com.gmcn.finanaceplanneraccounts.integration

import com.gmcn.finanaceplanneraccounts.dao.AccountDAO
import com.gmcn.finanaceplanneraccounts.dtos.ApiErrorDTO
import com.gmcn.finanaceplanneraccounts.dtos.CreateAccountDTO
import com.gmcn.finanaceplanneraccounts.dtos.CreateAccountResponseDTO
import com.gmcn.finanaceplanneraccounts.dtos.GetAccountResponseDTO
import com.gmcn.finanaceplanneraccounts.model.Account
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


const val TEST_USER_A_ACCOUNT_A_NAME = "Test Account A"
const val TEST_USER_A_ACCOUNT_A_CURRENCY = "EUR"

const val TEST_USER_A_ACCOUNT_B_NAME = "Test Account B"
const val TEST_USER_A_ACCOUNT_B_CURRENCY = "GBP"

const val TEST_USER_A_ACCOUNT_ADDITIONAL_NAME = "Another Test Account"
const val TEST_USER_A_ACCOUNT_ADDITIONAL_CURRENCY = "EUR"

class AccountsApiIntegrationTestsApi() : IntegrationTests(
) {
    @Autowired
    private lateinit var accountDAO: AccountDAO

    @BeforeAll
    override fun setup() {
        super.setup()

        setupTestUserAccounts()
    }

    @AfterAll
    override fun cleanup() {
        cleanupTestUserAccounts()

        super.cleanup()
    }

    private fun setupTestUserAccounts() {
        accountDAO.save(Account(TEST_USER_A_ACCOUNT_A_NAME, TEST_USER_A_ACCOUNT_A_CURRENCY, getTestAUserId()))
        accountDAO.save(Account(TEST_USER_A_ACCOUNT_B_NAME, TEST_USER_A_ACCOUNT_B_CURRENCY, getTestAUserId()))
    }

    private fun cleanupTestUserAccounts() {
        for (account in accountDAO.findByUserId(getTestAUserId())) {
            accountDAO.delete(account.id)
        }

        for (account in accountDAO.findByUserId(getTestBUserId())) {
            accountDAO.delete(account.id)
        }
    }

    @Test
    fun `Can get an account`() {
        authenticateAsUser(TEST_USER_A_EMAIL, TEST_USER_A_PASSWORD)
        val testAccountId = accountDAO.findByUserId(getTestAUserId())
            .find { it.name == TEST_USER_A_ACCOUNT_B_NAME }
            ?.id

        val entity = template.exchange(
            "/api/users/${getTestAUserId()}/accounts/$testAccountId",
            HttpMethod.GET,
            HttpEntity<Any>(getUserAuthenticationHeders()),
            GetAccountResponseDTO::class.java
        )

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)

        val account = entity.body

        assertNotNull(account)
        assertEquals(account.id, testAccountId)
        assertEquals(account.name, TEST_USER_A_ACCOUNT_B_NAME)
        assertEquals(account.currency, TEST_USER_A_ACCOUNT_B_CURRENCY)
        assertNotNull(account.balance)
    }

    @Test
    fun `Getting invalid account fails`() {
        authenticateAsUser(TEST_USER_A_EMAIL, TEST_USER_A_PASSWORD)

        val entity = template.exchange(
            "/api/users/${getTestAUserId()}/accounts/INEXISTENT_ACCOUNT",
            HttpMethod.GET,
            HttpEntity<Any>(getUserAuthenticationHeders()),
            ApiErrorDTO::class.java
        )

        assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `Getting someone else account fails`() {
        val testAccountId = accountDAO.findByUserId(getTestAUserId()).first().id

        authenticateAsUser(TEST_USER_B_EMAIL, TEST_USER_B_PASSWORD)

        val entity = template.exchange(
            "/api/users/${getTestBUserId()}/accounts/$testAccountId",
            HttpMethod.GET,
            HttpEntity<Any>(getUserAuthenticationHeders()),
            ApiErrorDTO::class.java
        )

        assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `Add an account`() {
        authenticateAsUser(TEST_USER_A_EMAIL, TEST_USER_A_PASSWORD)

        val createAccountDto =
            CreateAccountDTO(TEST_USER_A_ACCOUNT_ADDITIONAL_NAME, TEST_USER_A_ACCOUNT_ADDITIONAL_CURRENCY)

        val entity = template.exchange(
            "/api/users/${getTestAUserId()}/accounts",
            HttpMethod.POST,
            HttpEntity<CreateAccountDTO>(createAccountDto, getUserAuthenticationHeders()),
            CreateAccountResponseDTO::class.java
        )

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `Delete an account`() {
        authenticateAsUser(TEST_USER_A_EMAIL, TEST_USER_A_PASSWORD)

        val testAccountId = accountDAO.findByUserId(getTestAUserId()).first().id

        val entity = template.exchange(
            "/api/users/${getTestAUserId()}/accounts/$testAccountId",
            HttpMethod.DELETE,
            HttpEntity<Any>(getUserAuthenticationHeders()),
            CreateAccountResponseDTO::class.java
        )

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `Can get all accounts`() {
        authenticateAsUser(TEST_USER_A_EMAIL, TEST_USER_A_PASSWORD)

        val entity = template.exchange(
            "/api/users/${getTestAUserId()}/accounts",
            HttpMethod.GET,
            HttpEntity<Any>(getUserAuthenticationHeders()),
            Any::class.java
        )

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)

        val accounts: ArrayList<*>? = entity.body as? ArrayList<*>

        assertNotNull(accounts)
        assertEquals(2, accounts.size)
    }

}