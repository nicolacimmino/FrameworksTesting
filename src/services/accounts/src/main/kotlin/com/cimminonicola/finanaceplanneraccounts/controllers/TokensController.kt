package com.cimminonicola.finanaceplanneraccounts.controllers

import com.cimminonicola.finanaceplanneraccounts.ApplicationStatus
import com.cimminonicola.finanaceplanneraccounts.dtos.CreateTokenDTO
import com.cimminonicola.finanaceplanneraccounts.dtos.JWTTokenResponseDTO
import com.cimminonicola.finanaceplanneraccounts.entities.UsersRepository
import com.cimminonicola.finanaceplanneraccounts.errors.ResourceNotFoundApiException
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
class TokensController(private val usersRepository: UsersRepository) {
    @Autowired
    lateinit var applicationStatus: ApplicationStatus

    @PostMapping("")
    fun login(@RequestBody body: CreateTokenDTO): ResponseEntity<JWTTokenResponseDTO> {
        val ttlSeconds = 60 * 60 * 24
        val user = this.usersRepository.findByEmail(body.email)
            ?: throw ResourceNotFoundApiException("user/password invalid")

        if (!user.isPasswordValid(body.password)) {
            throw ResourceNotFoundApiException("user/password invalid")
        }

        val jwt = Jwts.builder()
            .setIssuer("exmaple.com")
            .setSubject(user.id)
            .setExpiration(Date(System.currentTimeMillis() + 1000 * ttlSeconds))
            .signWith(this.applicationStatus.getJWTKey())
            .compact()

        val jwtResponse = JWTTokenResponseDTO()
        jwtResponse.token = jwt
        jwtResponse.ttl = ttlSeconds

        return ResponseEntity.ok(jwtResponse)
    }
}