package com.gmcn.users.remoteservices

import com.gmcn.users.ConfigProperties
import com.gmcn.users.dtos.NewUserCredentialsDTO
import com.gmcn.users.dtos.ValidateTokenDTO
import com.gmcn.users.dtos.ValidateTokenResponseDTO
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.ClassMapper
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TokensService {
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
        val classMapper = DefaultJackson2JavaTypeMapper()
        classMapper.setTrustedPackages("*")
        classMapper.idClassMapping = mapOf(
            "tokens.validate_token" to ValidateTokenDTO::class.java,
            "tokens.validate_token_response" to ValidateTokenResponseDTO::class.java,
            "tokens.new_user_credentials" to NewUserCredentialsDTO::class.java,
        )
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

    fun validateToken(token: String): ValidateTokenResponseDTO? {
        return rabbitTemplate().convertSendAndReceive(
            configProperties.topicExchangeName, configProperties.userCreatedEventsRoutingKey, ValidateTokenDTO(
                token
            )
        ) as? ValidateTokenResponseDTO
    }
}