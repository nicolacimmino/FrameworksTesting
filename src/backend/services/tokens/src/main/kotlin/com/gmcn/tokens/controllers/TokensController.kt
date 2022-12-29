package com.gmcn.tokens.controllers

import com.gmcn.tokens.dtos.CreateTokenDTO
import com.gmcn.tokens.dtos.CreateTokenResponseDTO
import com.gmcn.tokens.services.TokensService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
//@CrossOrigin(origins = ["http://localhost:50707/"])
@CrossOrigin(origins = ["*"])
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