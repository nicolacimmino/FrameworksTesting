package com.cimminonicola.finanaceplanneraccounts

import com.cimminonicola.finanaceplanneraccounts.entities.Account
import com.cimminonicola.finanaceplanneraccounts.entities.AccountsRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.getForObject
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTestsApi(
    @Autowired val restTemplate: TestRestTemplate, @Autowired val accountsRepository: AccountsRepository
) {

    @BeforeAll
    fun setup() {
        println("Tests setup.")

    }

    @Test
    fun `Assert get accounts`() {
//        val entity = restTemplate.getForEntity<List<Account>>("/api/users/1/accounts")
//        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
//
//        val accounts = entity.body
//
//        assertThat(accounts).isNotNull
//
//        if (accounts != null) {
//            assertThat(accounts.count()).isNotZero
//            assertThat(accounts[0].id).isNotNull
//            assertThat(accounts[0].name).isNotBlank
//            assertThat(accounts[0].currency).isNotBlank
//        }
    }

    @Test
    fun `Assert get account`() {
//        val account = accountsRepository.save(Account("FunctionalTestsAccount_A", "PL2N"))
//
//        val entity = restTemplate.getForEntity<String>("/api/users/1/accounts/" + account.id)
//        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
//        assertThat(entity.body)
    }
}