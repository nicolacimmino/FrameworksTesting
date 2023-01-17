package com.gmcn.tokens.controllers

import com.gmcn.tokens.dtos.CreateTokenDTO
import com.gmcn.tokens.dtos.CreateTokenResponseDTO
import com.gmcn.tokens.services.TokensService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["http://localhost:8888/"])
class TokensController {
    @Autowired
    lateinit var tokensService: TokensService

    @PostMapping("api/tokens")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody createTokenRequest: CreateTokenDTO): CreateTokenResponseDTO {
        val (token, sub, ttlSeconds) = tokensService.createToken(createTokenRequest.email, createTokenRequest.password)

        return CreateTokenResponseDTO(token, ttlSeconds, sub)
    }
}