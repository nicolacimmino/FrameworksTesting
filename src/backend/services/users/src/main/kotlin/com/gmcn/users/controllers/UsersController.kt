package com.gmcn.users.controllers

import com.gmcn.users.ApplicationStatus
import com.gmcn.users.ConfigProperties
import com.gmcn.users.dao.UserDAO
import com.gmcn.users.dtos.CreateUserDTO
import com.gmcn.users.errors.InputInvalidApiException
import com.gmcn.users.errors.UnauthorizedApiException
import com.gmcn.users.model.User
import com.gmcn.users.service.UserService
import com.gmnc.isc.NewUserCredentialsDTO
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.DefaultClassMapper
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api")
class UsersController(
    private val userDAO: UserDAO
) {
    @Autowired
    lateinit var applicationStatus: ApplicationStatus

    @Autowired
    lateinit var userService: UserService

    @Autowired
    private val rabbitTemplate: RabbitTemplate? = null

    @Autowired
    private lateinit var configProperties: ConfigProperties

    @PostMapping("users")
    fun register(@RequestBody createUserRequest: CreateUserDTO): User {

        // TODO: should be moved to the service.
        if (userDAO.findByEmailOrNull(createUserRequest.email) != null) {
            throw InputInvalidApiException("duplicate email")
        }

        // TODO: Too much logic, most of the below belongs to the service layer.
        var user = User()
        user.name = createUserRequest.name
        user.email = createUserRequest.email

        user = userDAO.save(user)

        // NewUserCredentialsDTO exist in different packages in the sender and receiver,
        //  we need to provide an ID for the type and the receiver will use that to resolve its DTO
        // TODO: Consider, DTOs to communicate between services could be a in common library, they are already in
        //  the same namespace.
        var converter = Jackson2JsonMessageConverter()
        var classMapper = DefaultClassMapper()
        classMapper.setTrustedPackages("com.gmcn.isc")
        classMapper.setIdClassMapping(mapOf("new-user-credentials" to NewUserCredentialsDTO::class.java))
        converter.setClassMapper(classMapper)

        rabbitTemplate?.messageConverter = converter

        rabbitTemplate?.convertAndSend(
            configProperties.topicExchangeName, configProperties.userCreatedEventsRoutingKey, NewUserCredentialsDTO(
                user.id, createUserRequest.email, createUserRequest.password
            )
        );

        return user
    }

    @GetMapping("users/{user_id}")
    fun getUser(
        @PathVariable("user_id") userId: String?
    ): User {
        if (applicationStatus.authorizedUserId != userId) {
            throw UnauthorizedApiException()
        }

        return userService.getUser(userId)
    }
}