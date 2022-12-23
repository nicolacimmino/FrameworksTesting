package com.gmcn.finanaceplanneraccounts.controllers

import com.gmcn.finanaceplanneraccounts.dao.UserDAO
import com.gmcn.finanaceplanneraccounts.dtos.CreateUserDTO
import com.gmcn.finanaceplanneraccounts.errors.InputInvalidApiException
import com.gmcn.finanaceplanneraccounts.errors.UnauthorizedApiException
import com.gmcn.finanaceplanneraccounts.model.User
import com.gmcn.finanaceplanneraccounts.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api")
class UsersController(
    private val userDAO: UserDAO
) {
    @Autowired
    lateinit var applicationStatus: com.gmcn.finanaceplanneraccounts.ApplicationStatus

    @Autowired
    lateinit var userService: UserService

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