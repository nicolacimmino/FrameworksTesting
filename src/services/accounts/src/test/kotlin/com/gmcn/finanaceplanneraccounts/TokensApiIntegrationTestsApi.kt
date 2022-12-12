package com.gmcn.finanaceplanneraccounts

import com.gmcn.finanaceplanneraccounts.dtos.ApiErrorDTO
import com.gmcn.finanaceplanneraccounts.dtos.CreateTokenDTO
import com.gmcn.finanaceplanneraccounts.dtos.CreateTokenResponseDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus


class TokensApiIntegrationTestsApi : IntegrationTests(
) {

    @BeforeAll
    override fun setup() {
        super.setup()

        print("Test specific setup")
    }

    @Test
    fun `Can get a token`() {
        val createTokenDTO = CreateTokenDTO("test@example.com", "testpass")
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