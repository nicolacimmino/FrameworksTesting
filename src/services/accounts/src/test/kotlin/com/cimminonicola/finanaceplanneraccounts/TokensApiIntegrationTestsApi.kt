package com.cimminonicola.finanaceplanneraccounts

import com.cimminonicola.finanaceplanneraccounts.dtos.CreateTokenDTO
import com.cimminonicola.finanaceplanneraccounts.dtos.JWTTokenResponseDTO
import com.cimminonicola.finanaceplanneraccounts.entities.AccountsRepository
import com.cimminonicola.finanaceplanneraccounts.entities.User
import com.cimminonicola.finanaceplanneraccounts.entities.UsersRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
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
    @Autowired
    lateinit var usersRepository: UsersRepository

    @BeforeAll
    fun setup() {
        println("Tests setup.")

        var user = User()

        user.name = "testuser"
        user.email = "test@example.com"
        user.password = "testpass"

        this.usersRepository.save(user)

    }

    @AfterAll
    fun cleanup() {
        var testUserId = this.usersRepository.findByEmail("test@example.com")?.id

        if (testUserId != null) {
            this.usersRepository.deleteById(testUserId)
        }
    }

    @Test
    fun `Can get a token`() {
        val createTokenDTO = CreateTokenDTO("test@example.com", "testpass")
        val entity = restTemplate.postForEntity("/api/tokens", createTokenDTO, JWTTokenResponseDTO::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)

        val tokenResponse = entity.body

        assertThat(tokenResponse).isNotNull
        assertThat(tokenResponse?.token).isNotNull
        assertThat(tokenResponse?.ttl).isNotNull

    }
}