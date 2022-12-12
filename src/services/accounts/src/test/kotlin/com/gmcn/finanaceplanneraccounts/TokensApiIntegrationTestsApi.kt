package com.gmcn.finanaceplanneraccounts

import com.gmcn.finanaceplanneraccounts.dtos.ApiErrorDTO
import com.gmcn.finanaceplanneraccounts.dtos.CreateTokenDTO
import com.gmcn.finanaceplanneraccounts.dtos.CreateTokenResponseDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TokensApiIntegrationTestsApi : IntegrationTests(
) {
    @BeforeAll
    fun setup() {

        println("Tests setup.")

        setupTestUsers()
        setupRestTemplate()
    }

    @AfterAll
    fun cleanup() {
        val testUserId = usersRepository.findByEmail("test@example.com")?.id

        if (testUserId != null) {
            usersRepository.deleteById(testUserId)
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

        val entity = restTemplate.postForEntity("/api/tokens", createTokenDTO, ApiErrorDTO::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.UNAUTHORIZED)
    }

    @Test
    fun `Cannot get a token with bad password`() {
        val createTokenDTO = CreateTokenDTO("test@example.com", "awrongpassword")
        val entity = restTemplate.postForEntity("/api/tokens", createTokenDTO, ApiErrorDTO::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.UNAUTHORIZED)
    }
}