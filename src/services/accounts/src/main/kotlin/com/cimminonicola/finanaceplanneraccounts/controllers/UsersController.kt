package com.cimminonicola.finanaceplanneraccounts.controllers

import com.cimminonicola.finanaceplanneraccounts.ApplicationStatus
import com.cimminonicola.finanaceplanneraccounts.dtos.RegisterUserDTO
import com.cimminonicola.finanaceplanneraccounts.entities.User
import com.cimminonicola.finanaceplanneraccounts.entities.UsersRepository
import com.cimminonicola.finanaceplanneraccounts.errors.UnauthorizedApiException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api")
class UsersController(private val usersRepository: UsersRepository) {
    @Autowired
    lateinit var applicationStatus: ApplicationStatus

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
        @PathVariable("user_id") userId: String?
    ): User {
        if (this.applicationStatus.authorizedUserId != userId) {
            throw UnauthorizedApiException()
        }

        return usersRepository.findByIdOrNull(userId) ?: throw UnauthorizedApiException()
    }
}