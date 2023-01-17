package com.gmcn.userassets.services.remote

import com.gmcn.userassets.dtos.remote.tokens.ValidateTokenDTO
import com.gmcn.userassets.dtos.remote.tokens.ValidateTokenResponseDTO
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
        )
        return classMapper
    }

    fun rabbitTemplate(): RabbitTemplate {
        template.messageConverter = converter()

        return template
    }

    fun validateToken(token: String): ValidateTokenResponseDTO? {
        return rabbitTemplate().convertSendAndReceive(
            "financeplanner-topic-exchange", "tokens.service", ValidateTokenDTO(
                token
            )
        ) as? ValidateTokenResponseDTO
    }
}