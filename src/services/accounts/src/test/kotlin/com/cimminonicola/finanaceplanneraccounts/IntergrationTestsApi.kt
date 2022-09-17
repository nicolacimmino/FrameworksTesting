package com.cimminonicola.finanaceplanneraccounts

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTestsApi(@Autowired val restTemplate: TestRestTemplate) {

    @BeforeAll
    fun setup() {
        println("Tests setup.")
    }

    @Test
    fun `Assert get accounts`() {
        val entity = restTemplate.getForEntity<String>("/api/accounts")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        // TODO: add asserts on body content
    }

//    fun `Assert get account`() {
//        val entity = restTemplate.getForEntity<String>("/api/accounts/1")
//        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
//        // TODO: add asserts on body content
//    }
}