package com.gmcn.tokens

import com.gmcn.tokens.daos.UserCredentialsDAO
import com.gmcn.tokens.models.UserCredentials
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.client.ClientHttpResponse
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.DefaultResponseErrorHandler
import java.io.IOException

const val TEST_USER_A_ID = "abc123"
const val TEST_USER_A_EMAIL = "testa@example.com"
const val TEST_USER_A_PASSWORD = "testpassa"

const val TEST_USER_B_ID = "abc124"
const val TEST_USER_B_EMAIL = "testb@example.com"
const val TEST_USER_B_PASSWORD = "testpassb"

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class IntegrationTests {
    @Autowired
    lateinit var userCredentialsDAO: UserCredentialsDAO

    @Autowired
    lateinit var template: TestRestTemplate

    @BeforeAll
    fun setup() {

        println("Tests setup.")

        setupTestUsers()
        setupRestTemplate()
    }

    @AfterAll
    fun cleanup() {
        val testUserAId = userCredentialsDAO.findByEmailOrNull(TEST_USER_A_EMAIL)?.userId

        if (testUserAId != null) {
            userCredentialsDAO.delete(testUserAId)
        }

        val testUserBId = userCredentialsDAO.findByEmailOrNull(TEST_USER_B_EMAIL)?.userId

        if (testUserBId != null) {
            userCredentialsDAO.delete(testUserBId)
        }
    }

    private fun setupTestUsers() {
        // TODO: refactor!
        val testUserId = userCredentialsDAO.findByEmailOrNull(TEST_USER_A_EMAIL)?.userId

        if (testUserId == null) {
            val user = UserCredentials()

            user.userId = TEST_USER_A_ID
            user.email = TEST_USER_A_EMAIL
            user.password = TEST_USER_A_PASSWORD

            userCredentialsDAO.save(user)
        }

        val testUserBId = userCredentialsDAO.findByEmailOrNull(TEST_USER_B_EMAIL)?.userId

        if (testUserBId == null) {
            val user = UserCredentials()

            user.userId = TEST_USER_B_ID
            user.email = TEST_USER_B_EMAIL
            user.password = TEST_USER_B_PASSWORD

            userCredentialsDAO.save(user)
        }

    }

    protected fun getTestAUserId(): String {
        return userCredentialsDAO.findByEmailOrNull(TEST_USER_A_EMAIL)
            ?.userId
            ?: throw Exception("test user A not found")
    }

    protected fun getTestBUserId(): String {
        return userCredentialsDAO.findByEmailOrNull(TEST_USER_B_EMAIL)
            ?.userId
            ?: throw Exception("test user B not found")
    }

    private fun setupRestTemplate() {
        println("Setting up RestTemplate.")

        template.restTemplate.requestFactory = HttpComponentsClientHttpRequestFactory()
        template.restTemplate.errorHandler = object : DefaultResponseErrorHandler() {
            @Throws(IOException::class)
            override fun hasError(response: ClientHttpResponse): Boolean {
                val statusCode = response.statusCode
                return statusCode.is5xxServerError
            }
        }
    }
}