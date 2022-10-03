package com.cimminonicola.finanaceplanneraccounts

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import org.springframework.stereotype.Component
import java.security.Key
import javax.crypto.spec.SecretKeySpec

@Component
class ApplicationStatus {
    lateinit var authorizedUserId: String

    fun getJWTKey(): Key {
        // TODO: move elsewhere and get from a config file.
        return SecretKeySpec(
            Decoders.BASE64.decode("C5SDtWPWy7mKI5vfy6pA+5rKf+4u9XCnXmuuDWeyLTc="),
            SignatureAlgorithm.HS256.jcaName
        )
    }
}