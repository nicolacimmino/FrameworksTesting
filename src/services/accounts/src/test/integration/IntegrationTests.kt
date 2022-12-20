package com.gmcn.finanaceplanneraccounts.integration

import com.gmcn.finanaceplanneraccounts.dao.UserDAO
import com.gmcn.finanaceplanneraccounts.dtos.CreateTokenDTO
import com.gmcn.finanaceplanneraccounts.dtos.CreateTokenResponseDTO
import com.gmcn.finanaceplanneraccounts.errors.UnauthorizedApiException
import com.gmcn.finanaceplanneraccounts.model.User
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.DefaultResponseErrorHandler
import java.io.IOException

const val TEST_USER_A_NAME = "testusera"
const val TEST_USER_A_EMAIL = "testa@example.com"
const val TEST_USER_A_PASSWORD = "testpassa"

const val TEST_USER_B_NAME = "testuserb"
const val TEST_USER_B_EMAIL = "testb@example.com"
const val TEST_USER_B_PASSWORD = "testpassb"

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class IntegrationTests {
    @Autowired
    lateinit var userDAO: UserDAO

    @Autowired
    lateinit var template: TestRestTemplate

    var authenticatedUserToken: String? = null

    var authenticatedUserId: String? = null

    @BeforeAll
    fun setup() {

        println("Tests setup.")

        setupTestUsers()
        setupRestTemplate()
    }

    @AfterAll
    fun cleanup() {
        val testUserId = userDAO.findByEmailOrNull(TEST_USER_A_EMAIL)?.id

        if (testUserId != null) {
            userDAO.delete(testUserId)
        }
    }

    private fun setupTestUsers() {
        // TODO: refactor!
        val testUserId = userDAO.findByEmailOrNull(TEST_USER_A_EMAIL)?.id

        if (testUserId == null) {
            val user = User()

            user.name = TEST_USER_A_NAME
            user.email = TEST_USER_A_EMAIL
            user.password = TEST_USER_A_PASSWORD

            userDAO.save(user)
        }

        val testUserBId = userDAO.findByEmailOrNull(TEST_USER_B_EMAIL)?.id

        if (testUserBId == null) {
            val user = User()

            user.name = TEST_USER_B_NAME
            user.email = TEST_USER_B_EMAIL
            user.password = TEST_USER_B_PASSWORD

            userDAO.save(user)
        }

    }

    protected fun getTestAUserId(): String {
        return userDAO.findByEmailOrNull(TEST_USER_A_EMAIL)
            ?.id
            ?: throw Exception("test user A not found")
    }

    protected fun getTestBUserId(): String {
        return userDAO.findByEmailOrNull(TEST_USER_B_EMAIL)
            ?.id
            ?: throw Exception("test user B not found")
    }

    private fun setupRestTemplate() {
        println("Setting up RestTemplate.")

        template.restTemplate.requestFactory = HttpComponentsClientHttpRequestFactory()
        template.restTemplate.errorHandler = object : DefaultResponseErrorHandler() {
            @Throws(IOException::class)
            override fun hasError(response: ClientHttpResponse): Boolean {
                val statusCode = response.statusCode
                return statusCode.series() == HttpStatus.Series.SERVER_ERROR
            }
        }
    }

    protected fun authenticateAsUser(email: String, password: String) {
        val createTokenDTO = CreateTokenDTO(email, password)
        val entity = template.postForEntity("/api/tokens", createTokenDTO, CreateTokenResponseDTO::class.java)

        val response = (entity.body ?: throw UnauthorizedApiException())

        authenticatedUserToken = response.token
        authenticatedUserId = response.user_id
    }

    protected fun getUserAuthenticationHeders(): MultiValueMap<String, String> {
        val headers: MultiValueMap<String, String> = LinkedMultiValueMap()
        headers.add("Authorization", "Bearer $authenticatedUserToken")

        return headers
    }
}