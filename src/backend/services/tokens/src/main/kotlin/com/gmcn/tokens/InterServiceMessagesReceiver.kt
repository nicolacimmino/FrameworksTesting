package com.gmcn.tokens

import com.gmcn.tokens.dao.UserCredentialsDAO
import com.gmcn.tokens.model.UserCredentials
import com.gmnc.isc.NewUserCredentialsDTO
import com.gmnc.isc.ValidateTokenDTO
import com.gmnc.isc.ValidateTokenResponseDTO
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.amqp.support.converter.ClassMapper
import org.springframework.amqp.support.converter.DefaultClassMapper
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class InterServiceMessagesReceiver {
    @Autowired
    lateinit var userCredentialsDao: UserCredentialsDAO

    @Autowired
    lateinit var configProperties: ConfigProperties

    @Bean
    fun queue(): Queue? {
        return Queue(configProperties.serviceQueueName, false)
    }

    @Bean
    fun exchange(): TopicExchange? {
        return TopicExchange(configProperties.topicExchangeName)
    }

    @Bean
    fun binding(queue: Queue?, exchange: TopicExchange?): Binding? {
        return BindingBuilder.bind(queue).to(exchange).with(configProperties.userEventsRoutingKey)
    }

    @Bean
    fun classMapper(): ClassMapper {
        val classMapper = DefaultClassMapper()
        classMapper.setTrustedPackages("com.gmnc.isc")
        //classMapper.setIdClassMapping(mapOf("new-user-credentials" to NewUserCredentialsDTO::class.java))
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
        container.setQueueNames(configProperties.serviceQueueName)
        container.setMessageListener(messageListenerAdapter)
        messageListenerAdapter.setMessageConverter(messageConverter)

        return container
    }

    @Bean
    fun listenerAdapter(receiver: InterServiceMessagesReceiver?): MessageListenerAdapter? {
        return MessageListenerAdapter(receiver)
    }

    @RabbitHandler
    fun handleMessage(newUserCredentialsDto: NewUserCredentialsDTO) {
        println("Received <$newUserCredentialsDto>")

        var userCredentials = UserCredentials()
        userCredentials.userId = newUserCredentialsDto.userId
        userCredentials.email = newUserCredentialsDto.email
        userCredentials.password = newUserCredentialsDto.password

        this.userCredentialsDao.save(userCredentials)
    }

    private fun validateJwt(jwt: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(configProperties.getTokenKey())
            .build()
            .parseClaimsJws(jwt).body
    }

    @RabbitHandler
    fun handleMessage(validateTokenDTO: ValidateTokenDTO): ValidateTokenResponseDTO {
        println("Received <$validateTokenDTO>")

        try {
            val jwtBody = validateJwt(validateTokenDTO.token)

            return ValidateTokenResponseDTO(
                jwtBody.subject, true
            )
        } catch (e: Exception) {
            return ValidateTokenResponseDTO(
                "", false
            )
        }
    }
}