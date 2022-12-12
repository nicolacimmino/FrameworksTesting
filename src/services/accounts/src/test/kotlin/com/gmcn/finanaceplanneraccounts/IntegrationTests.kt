package com.gmcn.finanaceplanneraccounts

import com.gmcn.finanaceplanneraccounts.datasource.UserDataSource
import com.gmcn.finanaceplanneraccounts.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.DefaultResponseErrorHandler
import java.io.IOException

abstract class IntegrationTests {
    @Autowired
    lateinit var usersRepository: UserDataSource

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    fun setupTestUsers() {
        println("Setting up users.")

        val testUserId = usersRepository.findByEmail("test@example.com")?.id

        if (testUserId == null) {
            val user = User()

            user.name = "testuser"
            user.email = "test@example.com"
            user.password = "testpass"

            usersRepository.save(user)
        }

    }

    fun setupRestTemplate() {
        println("Setting up RestTemplate.")

        restTemplate.restTemplate.requestFactory = HttpComponentsClientHttpRequestFactory()
        restTemplate.restTemplate.errorHandler = object : DefaultResponseErrorHandler() {
            @Throws(IOException::class)
            override fun hasError(response: ClientHttpResponse): Boolean {
                val statusCode = response.statusCode
                return statusCode.series() == HttpStatus.Series.SERVER_ERROR
            }
        }
    }
}