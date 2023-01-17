package com.gmcn.tokens.services

import com.gmcn.tokens.daos.UserCredentialsDAO
import com.gmcn.tokens.errors.UnauthorizedApiException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
@Configuration
class TokensService() {
    @Value("\${jwt_key}")
    lateinit var jwtKey: String

    @Value("\${jwt_ttl_seconds}")
    lateinit var jwtTtlSeconds: Number

    @Autowired
    private lateinit var userCredentialsDAO: UserCredentialsDAO

    @Autowired
    private lateinit var userCredentialsService: UserCredentialsService

    fun createToken(
        email: String, password: String
    ): Token {

        val userCredentials =
            userCredentialsDAO.findByEmailOrNull(email) ?: throw UnauthorizedApiException("user/password invalid")

        if (!userCredentialsService.isPasswordValid(userCredentials, password)) {
            throw UnauthorizedApiException("user/password invalid")
        }

        return Token(
            Jwts.builder().setIssuer("example.com")
                .setSubject(userCredentials.userId)
                .setExpiration(Date(System.currentTimeMillis() + 1000 * jwtTtlSeconds.toInt()))
                .signWith(getTokenKey())
                .compact(),
            userCredentials.userId,
            jwtTtlSeconds.toInt()
        )
    }

    fun validateToken(token: String): String? {
        try {
            val jwtBody = validateJwt(token)

            return jwtBody.subject
        } catch (e: Exception) {
            return null
        }
    }

    private fun validateJwt(jwt: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(getTokenKey())
            .build()
            .parseClaimsJws(jwt).body
    }

    private fun getTokenKey(): Key {
        return SecretKeySpec(
            Decoders.BASE64.decode(jwtKey ?: ""), SignatureAlgorithm.HS256.jcaName
        )
    }
}

data class Token(
    val compacted: String,
    val sub: String,
    val ttlSeconds: Int
) {

}
