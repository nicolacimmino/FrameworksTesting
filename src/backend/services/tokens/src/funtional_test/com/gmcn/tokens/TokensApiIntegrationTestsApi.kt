package com.gmcn.tokens

import com.gmcn.tokens.dtos.ApiErrorDTO
import com.gmcn.tokens.dtos.CreateTokenDTO
import com.gmcn.tokens.dtos.CreateTokenResponseDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class TokensApiIntegrationTestsApi : IntegrationTests(
) {

    @BeforeAll
    override fun setup() {
        super.setup()
    }

    @AfterAll
    override fun cleanup() {
        super.cleanup()
    }

    @Test
    fun `Can get a token`() {
        val createTokenDTO = CreateTokenDTO(TEST_USER_A_EMAIL, TEST_USER_A_PASSWORD)
        val entity = template.postForEntity("/api/tokens", createTokenDTO, CreateTokenResponseDTO::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)

        val tokenResponse = entity.body

        assertThat(tokenResponse).isNotNull
        assertThat(tokenResponse?.token).isNotNull
        assertThat(tokenResponse?.ttl).isNotNull
    }

    @Test
    fun `Cannot get a token with bad user`() {
        val createTokenDTO = CreateTokenDTO("dummyuser@example.com", "awrongpassword")

        val entity = template.postForEntity("/api/tokens", createTokenDTO, ApiErrorDTO::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.UNAUTHORIZED)
    }

    @Test
    fun `Cannot get a token with bad password`() {
        val createTokenDTO = CreateTokenDTO("test@example.com", "awrongpassword")
        val entity = template.postForEntity("/api/tokens", createTokenDTO, ApiErrorDTO::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.UNAUTHORIZED)
    }
}