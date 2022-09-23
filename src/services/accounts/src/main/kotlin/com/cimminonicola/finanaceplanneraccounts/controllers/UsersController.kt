package com.cimminonicola.finanaceplanneraccounts.controllers

import com.cimminonicola.finanaceplanneraccounts.dtos.RegisterUserDTO
import com.cimminonicola.finanaceplanneraccounts.entities.User
import com.cimminonicola.finanaceplanneraccounts.entities.UsersRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}