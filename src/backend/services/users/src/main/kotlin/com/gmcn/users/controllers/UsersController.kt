package com.gmcn.users.controllers

import com.gmcn.users.ApplicationStatus
import com.gmcn.users.dtos.*
import com.gmcn.users.errors.UnauthorizedApiException
import com.gmcn.users.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api")
class UsersController(
) {
    @Autowired
    lateinit var applicationStatus: ApplicationStatus

    @Autowired
    lateinit var userService: UserService

    @PostMapping("users")
    fun register(@RequestBody createUserRequest: CreateUserDTO): CreateUserResponseDTO {

        val user = userService.createUser(
            createUserRequest.email, createUserRequest.name, createUserRequest.password
        )

        return CreateUserResponseDTO(
            user.id, user.name, user.email
        )
    }

    @GetMapping("users/{user_id}")
    fun getUser(
        @PathVariable("user_id") userId: String?
    ): GetUserResponseDTO {
        if (applicationStatus.authorizedUserId != userId) {
            throw UnauthorizedApiException()
        }

        val user = userService.getUser(userId)

        return GetUserResponseDTO(
            user.id, user.name, user.email
        )
    }

    @PatchMapping("users/{user_id}")
    fun updatePassword(@RequestBody updatePasswordRequest: UpdateUserPasswordDTO): UpdateUserPasswordResponseDTO {
        userService.updatePassword(
            applicationStatus.authorizedUserId,
            updatePasswordRequest.password
        )

        var user = userService.getUser(applicationStatus.authorizedUserId)

        return UpdateUserPasswordResponseDTO(
            user.id, user.name, user.email
        )
    }
}