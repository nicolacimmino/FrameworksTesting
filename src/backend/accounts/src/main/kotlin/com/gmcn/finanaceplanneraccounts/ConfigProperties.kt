package com.gmcn.finanaceplanneraccounts

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.security.Key
import javax.crypto.spec.SecretKeySpec


@Configuration
@ConfigurationProperties(prefix = "financeplanner")
class ConfigProperties {
    var jwtkey: String? = null

    fun getTokenKey(): Key {
        return SecretKeySpec(
            Decoders.BASE64.decode(jwtkey ?: ""),
            SignatureAlgorithm.HS256.jcaName
        )
    }
}
