package com.gmcn.tokens.controllers

import com.gmcn.tokens.services.Token
import com.gmcn.tokens.services.TokensService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(TokensController::class)
internal class TokensControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var service: TokensService

    @Test
    fun create() {

        Mockito.`when`(service.createToken("user@example.com", "test")).thenReturn(
            Token("eyj123", "123", 86400)
        )

        mockMvc.post("/api/tokens") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\"email\": \"user@example.com\",\"password\": \"test\"}"
        }.andExpect {
            status { isOk() }
            content { json("{\"token\": \"eyj123\"}") }
            content { json("{\"user_id\": \"123\"}") }
            content { json("{\"ttl\": 86400}") }
        }
    }
}