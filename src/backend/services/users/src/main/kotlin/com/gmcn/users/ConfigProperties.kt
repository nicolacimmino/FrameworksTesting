package com.gmcn.users

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@ConfigurationProperties(prefix = "financeplanner")
class ConfigProperties {

    lateinit var topicExchangeName: String

    lateinit var tokensServiceQueueName: String

    lateinit var userCreatedEventsRoutingKey: String

}
