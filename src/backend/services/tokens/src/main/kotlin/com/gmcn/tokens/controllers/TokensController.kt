package com.gmcn.tokens.controllers

import com.gmcn.tokens.dao.UserCredentialsDAO
import com.gmcn.tokens.dtos.CreateTokenDTO
import com.gmcn.tokens.dtos.CreateTokenResponseDTO
import com.gmcn.tokens.errors.UnauthorizedApiException
import com.gmcn.tokens.service.TokensService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/tokens")
class TokensController() {
    @Autowired
    lateinit var tokensService: TokensService

    @Autowired
    lateinit var userCredentialsDao: UserCredentialsDAO

    @PostMapping
    fun login(@RequestBody createTokenRequest: CreateTokenDTO): ResponseEntity<CreateTokenResponseDTO> {
        val user = userCredentialsDao.findByEmailOrNull(createTokenRequest.email)
            ?: throw UnauthorizedApiException("user/password invalid")

        var token = tokensService.createToken(createTokenRequest.email, createTokenRequest.password)

        return ResponseEntity.ok(CreateTokenResponseDTO(token, 123 /* TODO: get from the service */, user.userId))
    }
}