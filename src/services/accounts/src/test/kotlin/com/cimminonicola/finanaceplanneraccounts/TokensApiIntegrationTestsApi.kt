package com.cimminonicola.finanaceplanneraccounts

import com.cimminonicola.finanaceplanneraccounts.dtos.CreateTokenDTO
import com.cimminonicola.finanaceplanneraccounts.dtos.CreateTokenResponseDTO
import com.cimminonicola.finanaceplanneraccounts.datasource.AccountDataSource
import com.cimminonicola.finanaceplanneraccounts.model.User
import com.cimminonicola.finanaceplanneraccounts.datasource.UserDataSource
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
    @Autowired val restTemplate: TestRestTemplate, @Autowired val accountsRepository: AccountDataSource
) {
    @Autowired
    lateinit var usersRepository: UserDataSource

    @BeforeAll
    fun setup() {
        println("Tests setup.")

        var testUserId = this.usersRepository.findByEmail("test@example.com")?.id

        if (testUserId == null) {
            var user = User()

            user.name = "testuser"
            user.email = "test@example.com"
            user.password = "testpass"

            this.usersRepository.save(user)
        }
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
        val entity = restTemplate.postForEntity("/api/tokens", createTokenDTO, CreateTokenResponseDTO::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)

        val tokenResponse = entity.body

        assertThat(tokenResponse).isNotNull
        assertThat(tokenResponse?.token).isNotNull
        assertThat(tokenResponse?.ttl).isNotNull
    }

    @Test
    fun `Cannot get a token with bad user`() {
        val createTokenDTO = CreateTokenDTO("dummyuser@example.com", "awrongpassword")
        val entity = restTemplate.postForEntity("/api/tokens", createTokenDTO, CreateTokenResponseDTO::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `Cannot get a token with bad password`() {
        val createTokenDTO = CreateTokenDTO("test@example.com", "awrongpassword")
        val entity = restTemplate.postForEntity("/api/tokens", createTokenDTO, CreateTokenResponseDTO::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }
}