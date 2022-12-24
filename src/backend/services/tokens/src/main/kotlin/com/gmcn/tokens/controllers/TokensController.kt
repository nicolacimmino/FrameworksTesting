package com.gmcn.tokens.controllers

import com.gmcn.tokens.ConfigProperties
import com.gmcn.tokens.dao.UserCredentialsDAO
import com.gmcn.tokens.dtos.CreateTokenDTO
import com.gmcn.tokens.dtos.CreateTokenResponseDTO
import com.gmcn.tokens.errors.UnauthorizedApiException
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
class TokensController(private val userDAO: UserCredentialsDAO) {

    @Autowired
    lateinit var configProperties: ConfigProperties

    @PostMapping
    fun login(@RequestBody createTokenRequest: CreateTokenDTO): ResponseEntity<CreateTokenResponseDTO> {
        val ttlSeconds = 60 * 60 * 24
        val user = userDAO.findByEmailOrNull(createTokenRequest.email)
            ?: throw UnauthorizedApiException("user/password invalid")

        if (!user.isPasswordValid(createTokenRequest.password)) {
            throw UnauthorizedApiException("user/password invalid")
        }

        val jwt = Jwts.builder()
            .setIssuer("example.com")
            .setSubject(user.userId)
            .setExpiration(Date(System.currentTimeMillis() + 1000 * ttlSeconds))
            .signWith(configProperties.getTokenKey())
            .compact()

        return ResponseEntity.ok(CreateTokenResponseDTO(jwt, ttlSeconds, user.userId))
    }
}