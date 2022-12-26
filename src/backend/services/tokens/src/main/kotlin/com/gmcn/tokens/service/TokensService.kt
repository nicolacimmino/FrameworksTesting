package com.gmcn.tokens.service

import com.gmcn.tokens.dao.UserCredentialsDAO
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
    @Value("\${jwtkey}")
    lateinit var jwtkey: String

    @Autowired
    private lateinit var userDAO: UserCredentialsDAO

    fun createToken(
        email: String, password: String
    ): String {
        val ttlSeconds = 60 * 60 * 24
        val user = userDAO.findByEmailOrNull(email) ?: throw UnauthorizedApiException("user/password invalid")

        if (!user.isPasswordValid(password)) {
            throw UnauthorizedApiException("user/password invalid")
        }

        return Jwts.builder().setIssuer("example.com").setSubject(user.userId)
            .setExpiration(Date(System.currentTimeMillis() + 1000 * ttlSeconds)).signWith(getTokenKey()).compact()
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
            Decoders.BASE64.decode(jwtkey ?: ""), SignatureAlgorithm.HS256.jcaName
        )
    }
}
