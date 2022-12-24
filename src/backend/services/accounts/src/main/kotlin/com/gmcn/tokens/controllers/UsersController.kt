package com.gmcn.tokens.controllers

import com.gmcn.tokens.dao.UserDAO
import com.gmcn.tokens.dtos.CreateUserDTO
import com.gmcn.tokens.dtos.NewUserCredentialsDTO
import com.gmcn.tokens.errors.InputInvalidApiException
import com.gmcn.tokens.errors.UnauthorizedApiException
import com.gmcn.tokens.model.User
import com.gmcn.tokens.service.UserService
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api")
class UsersController(
    private val userDAO: UserDAO
) {
    @Autowired
    lateinit var applicationStatus: com.gmcn.tokens.ApplicationStatus

    @Autowired
    lateinit var userService: UserService

    @Autowired
    private val rabbitTemplate: RabbitTemplate? = null

    @PostMapping("users")
    fun register(@RequestBody createUserRequest: CreateUserDTO): User {

        // TODO: this is supposed to be enforced by the model unique constraint but doesn't work!
        // Also, it should be moved to the service.
        if (userDAO.findByEmailOrNull(createUserRequest.email) != null) {
            throw InputInvalidApiException("duplicate email")
        }

        val user = User()
        user.name = createUserRequest.name
        user.email = createUserRequest.email
        user.password = createUserRequest.password

        rabbitTemplate?.messageConverter = Jackson2JsonMessageConverter()

        rabbitTemplate?.convertAndSend(
            "spring-boot-exchange", "foo.bar.baz", NewUserCredentialsDTO(
                createUserRequest.name,
                createUserRequest.password
            )
        );

        return userDAO.save(user)
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