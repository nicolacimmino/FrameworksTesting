package com.gmcn.tokens

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

    lateinit var topicExchangeName: String

    lateinit var tokensServiceQueueName: String

    lateinit var userCreatedEventsRoutingKey: String

    fun getTokenKey(): Key {
        return SecretKeySpec(
            Decoders.BASE64.decode(jwtkey ?: ""),
            SignatureAlgorithm.HS256.jcaName
        )
    }
}
