package com.cimminonicola.finanaceplanneraccounts.controllers

import com.cimminonicola.finanaceplanneraccounts.ConfigProperties
import com.cimminonicola.finanaceplanneraccounts.datasource.UserDataSource
import com.cimminonicola.finanaceplanneraccounts.dtos.CreateTokenDTO
import com.cimminonicola.finanaceplanneraccounts.dtos.CreateTokenResponseDTO
import com.cimminonicola.finanaceplanneraccounts.errors.UnauthorizedApiException
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("api/tokens")
class TokensController(private val usersRepository: UserDataSource) {

    @Autowired
    lateinit var configProperties: ConfigProperties

    @PostMapping
    fun login(@RequestBody createTokenRequest: CreateTokenDTO): ResponseEntity<CreateTokenResponseDTO> {
        val ttlSeconds = 60 * 60 * 24
        val user = usersRepository.findByEmail(createTokenRequest.email)
            ?: throw UnauthorizedApiException("user/password invalid")

        if (!user.isPasswordValid(createTokenRequest.password)) {
            throw UnauthorizedApiException("user/password invalid")
        }

        val jwt = Jwts.builder()
            .setIssuer("example.com")
            .setSubject(user.id)
            .setExpiration(Date(System.currentTimeMillis() + 1000 * ttlSeconds))
            .signWith(configProperties.getTokenKey())
            .compact()

        return ResponseEntity.ok(CreateTokenResponseDTO(jwt, ttlSeconds, user.id))
    }
}