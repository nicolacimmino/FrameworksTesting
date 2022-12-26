package com.gmcn.users.isc

import com.gmcn.users.ConfigProperties
import com.gmnc.isc.NewUserCredentialsDTO
import com.gmnc.isc.ValidateTokenDTO
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.ClassMapper
import org.springframework.amqp.support.converter.DefaultClassMapper
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class InterServiceMessagesSender {
    @Autowired
    private lateinit var configProperties: ConfigProperties

    @Autowired
    lateinit var template: RabbitTemplate

    fun converter(): MessageConverter {
        val converter = Jackson2JsonMessageConverter()

        converter.setClassMapper(classMapper())

        return converter
    }

    fun classMapper(): ClassMapper {
        val classMapper = DefaultClassMapper()
        classMapper.setTrustedPackages("com.gmcn.isc")
        return classMapper
    }

    fun rabbitTemplate(): RabbitTemplate {
        template.messageConverter = converter()

        return template
    }

    fun notifyNewUserCredentials(userId: String, email: String, password: String) {
        rabbitTemplate().convertAndSend(
            configProperties.topicExchangeName, configProperties.userCreatedEventsRoutingKey, NewUserCredentialsDTO(
                userId, email, password
            )
        );
    }

    fun validateToken(token: String): String? {

        return rabbitTemplate().convertSendAndReceive(
            configProperties.topicExchangeName, configProperties.userCreatedEventsRoutingKey, ValidateTokenDTO(
                token
            )
        ) as? String
    }
}