package com.cimminonicola.finanaceplanneraccounts.controllers

import com.cimminonicola.finanaceplanneraccounts.ApplicationStatus
import com.cimminonicola.finanaceplanneraccounts.dtos.CreateUserDTO
import com.cimminonicola.finanaceplanneraccounts.model.User
import com.cimminonicola.finanaceplanneraccounts.datasource.UserDataSource
import com.cimminonicola.finanaceplanneraccounts.errors.UnauthorizedApiException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api")
class UsersController(private val usersRepository: UserDataSource) {
    @Autowired
    lateinit var applicationStatus: ApplicationStatus

    @PostMapping("users")
    fun register(@RequestBody body: CreateUserDTO): User {
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