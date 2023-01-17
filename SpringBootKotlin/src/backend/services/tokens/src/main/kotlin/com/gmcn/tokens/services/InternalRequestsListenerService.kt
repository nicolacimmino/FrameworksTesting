package com.gmcn.tokens.services

import com.gmcn.tokens.dtos.NewUserCredentialsDTO
import com.gmcn.tokens.dtos.NewUserPasswordDTO
import com.gmcn.tokens.dtos.ValidateTokenDTO
import com.gmcn.tokens.dtos.ValidateTokenResponseDTO
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.amqp.support.converter.ClassMapper
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class InternalRequestsListenerService {
    @Autowired
    private lateinit var tokensService: TokensService

    @Bean
    fun queue(): Queue? {
        return Queue("financeplanner-tokens-service", false)
    }

    @Bean
    fun exchange(): TopicExchange? {
        return TopicExchange("financeplanner-topic-exchange")
    }

    @Bean
    fun binding(queue: Queue?, exchange: TopicExchange?): Binding? {
        return BindingBuilder.bind(queue).to(exchange).with("tokens.service")
    }

    @Bean
    fun classMapper(): ClassMapper {
        val classMapper = DefaultJackson2JavaTypeMapper()
        classMapper.setTrustedPackages("*")
        classMapper.idClassMapping = mapOf(
            "tokens.validate_token" to ValidateTokenDTO::class.java,
            "tokens.validate_token_response" to ValidateTokenResponseDTO::class.java,
            "tokens.new_user_credentials" to NewUserCredentialsDTO::class.java,
            "tokens.new_user_password" to NewUserPasswordDTO::class.java
        )
        return classMapper

    }

    @Bean
    fun messageConverter(classMapper: ClassMapper): MessageConverter {
        val converter = Jackson2JsonMessageConverter()
        converter.setClassMapper(classMapper)

        return converter
    }

    @Bean
    fun container(
        connectionFactory: ConnectionFactory?,
        messageListenerAdapter: MessageListenerAdapter,
        messageConverter: MessageConverter
    ): SimpleMessageListenerContainer? {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory!!
        container.setQueueNames("financeplanner-tokens-service")
        container.setMessageListener(messageListenerAdapter)
        messageListenerAdapter.setMessageConverter(messageConverter)

        return container
    }

    @Bean
    fun listenerAdapter(receiver: InternalRequestsListenerService?): MessageListenerAdapter? {
        return MessageListenerAdapter(receiver)
    }

    @Autowired
    private lateinit var userCredentialsService: UserCredentialsService

    @RabbitHandler
    fun handleMessage(newUserCredentialsDto: NewUserCredentialsDTO) {
        userCredentialsService.updateUserCredentials(
            newUserCredentialsDto.userId,
            newUserCredentialsDto.email, newUserCredentialsDto.password
        )
    }

    @RabbitHandler
    fun handleMessage(validateTokenDTO: ValidateTokenDTO): ValidateTokenResponseDTO {
        val subject = tokensService.validateToken(validateTokenDTO.token)

        return ValidateTokenResponseDTO(
            subject ?: "", subject != null
        )
    }

    @RabbitHandler
    fun handleMessage(newUserPasswordDTO: NewUserPasswordDTO) {
        userCredentialsService.updateUserPassword(
            newUserPasswordDTO.userId,
            newUserPasswordDTO.password
        )
    }
}