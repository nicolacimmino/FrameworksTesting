package com.gmcn.users.controllers

import com.gmcn.users.dtos.*
import com.gmcn.users.errors.UnauthorizedApiException
import com.gmcn.users.services.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api")
@CrossOrigin(origins = ["http://localhost:8888/"])
class UsersController(
) {
    @Autowired
    lateinit var userService: UserService

    @PostMapping("users")
    fun register(@RequestBody createUserRequest: CreateUserDTO): ResponseEntity<CreateUserResponseDTO> {

        val user = userService.createUser(
            createUserRequest.email, createUserRequest.name, createUserRequest.password
        )

        return ResponseEntity<CreateUserResponseDTO>(
            CreateUserResponseDTO(
                user.id, user.name, user.email
            ), null, HttpServletResponse.SC_CREATED
        )
    }

    @GetMapping("users/{user_id}")
    fun getUser(
        request: HttpServletRequest, @PathVariable("user_id") userId: String
    ): GetUserResponseDTO {
        if (request.getAttribute("auth.user_id").toString() != userId) {
            throw UnauthorizedApiException()
        }

        val user = userService.getUser(userId)

        return GetUserResponseDTO(
            user.id, user.name, user.email
        )
    }

    @PatchMapping("users/{user_id}")
    fun updatePassword(
        request: HttpServletRequest,
        @RequestBody updatePasswordRequest: UpdateUserPasswordDTO
    ): UpdateUserPasswordResponseDTO {
        userService.updatePassword(
            request.getAttribute("auth.user_id").toString(),
            updatePasswordRequest.password
        )

        var user = userService.getUser(request.getAttribute("auth.user_id").toString())

        return UpdateUserPasswordResponseDTO(
            user.id, user.name, user.email
        )
    }
}