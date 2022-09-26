package com.cimminonicola.finanaceplanneraccounts.controllers

import com.cimminonicola.finanaceplanneraccounts.dtos.JWTTokenResponseDTO
import com.cimminonicola.finanaceplanneraccounts.dtos.LoginDTO
import com.cimminonicola.finanaceplanneraccounts.entities.UsersRepository
import com.cimminonicola.finanaceplanneraccounts.errors.ResourceNotFoundApiException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec


@RestController
@RequestMapping("api")
class AuthController(private val usersRepository: UsersRepository) {

    @PostMapping("login")
    fun login(@RequestBody body: LoginDTO): ResponseEntity<JWTTokenResponseDTO> {
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
            .signWith(this.getKey())
            .compact()

        var jwtResponse = JWTTokenResponseDTO()
        jwtResponse.token = jwt
        jwtResponse.ttl = ttlSeconds

        return ResponseEntity.ok(jwtResponse)
    }

    fun validateJwt(jwt: String) : Claims {
        return Jwts.parserBuilder()
            .setSigningKey(this.getKey())
            .build()
            .parseClaimsJws(jwt).body
    }

    private fun getKey(): Key {
        // TODO: move elsewhere and get from a config file.
        return SecretKeySpec(
            Decoders.BASE64.decode("C5SDtWPWy7mKI5vfy6pA+5rKf+4u9XCnXmuuDWeyLTc="),
            SignatureAlgorithm.HS256.jcaName
        )
    }
}