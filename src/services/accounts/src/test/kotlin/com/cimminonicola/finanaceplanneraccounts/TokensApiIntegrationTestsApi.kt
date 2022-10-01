package com.cimminonicola.finanaceplanneraccounts

import com.cimminonicola.finanaceplanneraccounts.dtos.CreateTokenDTO
import com.cimminonicola.finanaceplanneraccounts.entities.AccountsRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TokensApiIntegrationTestsApi(
    @Autowired val restTemplate: TestRestTemplate, @Autowired val accountsRepository: AccountsRepository
) {

    @BeforeAll
    fun setup() {
        println("Tests setup.")

    }

    @Test
    fun `Can get a token`() {
        val entity = restTemplate.getForEntity("/api/tokens", CreateTokenDTO::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)

        val accounts = entity.body

        assertThat(accounts).isNotNull

        if (accounts != null) {
            assertThat(accounts.count()).isNotZero
            assertThat(accounts[0].id).isNotNull
            assertThat(accounts[0].name).isNotBlank
            assertThat(accounts[0].currency).isNotBlank
        }
    }
}