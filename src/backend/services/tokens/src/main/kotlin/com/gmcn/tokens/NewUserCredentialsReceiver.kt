package com.gmcn.tokens

import com.gmcn.tokens.dao.UserCredentialsDAO
import com.gmcn.tokens.model.UserCredentials
import com.gmcn.users.dtos.NewUserCredentialsDTO
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.amqp.support.converter.DefaultClassMapper
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class NewUserCredentialsReceiver {
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


    fun messageConverter(): MessageConverter? {

        val converter = Jackson2JsonMessageConverter()
        val classMapper = DefaultClassMapper()
        classMapper.setTrustedPackages("*")
        classMapper.setIdClassMapping(mapOf("NewUserCredentialsDTO" to NewUserCredentialsDTO::class.java))
        converter.setClassMapper(classMapper)

        return converter
    }

    @Bean
    fun container(
        connectionFactory: ConnectionFactory?,
        messageListenerAdapter: MessageListenerAdapter
    ): SimpleMessageListenerContainer? {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory!!
        container.setQueueNames(configProperties.serviceQueueName)
        container.setMessageListener(messageListenerAdapter)
        messageListenerAdapter.setMessageConverter(messageConverter())

        return container
    }

    @Bean
    fun listenerAdapter(receiver: NewUserCredentialsReceiver?): MessageListenerAdapter? {
        return MessageListenerAdapter(receiver, "receiveMessage")
    }

    fun receiveMessage(newUserCredentialsDto: NewUserCredentialsDTO) {
        println("Received <$newUserCredentialsDto>")

        var userCredentials = UserCredentials()
        userCredentials.userId = newUserCredentialsDto.userId
        userCredentials.email = newUserCredentialsDto.email
        userCredentials.password = newUserCredentialsDto.password

        this.userCredentialsDao.save(userCredentials)
    }
}