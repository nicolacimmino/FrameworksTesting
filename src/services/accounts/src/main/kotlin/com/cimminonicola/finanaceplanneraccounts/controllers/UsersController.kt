package com.cimminonicola.finanaceplanneraccounts.controllers

import com.cimminonicola.finanaceplanneraccounts.dtos.RegisterUserDTO
import com.cimminonicola.finanaceplanneraccounts.entities.User
import com.cimminonicola.finanaceplanneraccounts.entities.UsersRepository
import com.cimminonicola.finanaceplanneraccounts.errors.UnauthorizedApiException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
@RequestMapping("api")
class UsersController(private val usersRepository: UsersRepository) {

    @PostMapping("users")
    fun register(@RequestBody body: RegisterUserDTO): User {
        val user = User()

        user.name = body.name
        user.email = body.email
        user.password = body.password

        this.usersRepository.save(user)

        return user
    }

    @GetMapping("users/{user_id}")
    fun getUser(
        @RequestHeader("Authorization") auth: String?,
        @PathParam("user_id") userId: String?
    ): User {
        val authController = AuthController(usersRepository)

        if (auth == null) {
            throw UnauthorizedApiException()
        }

        val jwt = auth.substringAfter("Bearer ")
            .substringAfter("bearer ")

        try {
            var jwtBody = authController.validateJwt(jwt)

            if (jwtBody.subject != userId) {
                throw UnauthorizedApiException()
            }

            return usersRepository.findByIdOrNull(userId)
                ?: throw UnauthorizedApiException()

        } catch (e: Exception) {
            throw UnauthorizedApiException()
        }


    }
}