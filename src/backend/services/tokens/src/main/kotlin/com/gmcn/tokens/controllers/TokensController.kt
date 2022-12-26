package com.gmcn.tokens.controllers

import com.gmcn.tokens.daos.UserCredentialsDAO
import com.gmcn.tokens.dtos.CreateTokenDTO
import com.gmcn.tokens.dtos.CreateTokenResponseDTO
import com.gmcn.tokens.errors.UnauthorizedApiException
import com.gmcn.tokens.services.TokensService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TokensController() {
    @Autowired
    lateinit var tokensService: TokensService

    @Autowired
    lateinit var userCredentialsDao: UserCredentialsDAO

    @Value("\${jwt_ttl_seconds}")
    lateinit var jwtTtlSeconds: Number

    @PostMapping("api/tokens")
    fun create(@RequestBody createTokenRequest: CreateTokenDTO): ResponseEntity<CreateTokenResponseDTO> {
        val user = userCredentialsDao.findByEmailOrNull(createTokenRequest.email)
            ?: throw UnauthorizedApiException("user/password invalid")

        val token = tokensService.createToken(createTokenRequest.email, createTokenRequest.password)

        return ResponseEntity.ok(CreateTokenResponseDTO(token, jwtTtlSeconds.toInt(), user.userId))
    }
}