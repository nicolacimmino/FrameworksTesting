package com.gmcn.tokens

import com.gmcn.tokens.dao.UserCredentialsDAO
import com.gmcn.users.dtos.NewUserCredentialsDTO
import com.gmcn.tokens.model.UserCredentials
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NewUserCredentialsReceiver {
    @Autowired
    lateinit var userCredentialsDao: UserCredentialsDAO

    fun receiveMessage(newUserCredentialsDto: NewUserCredentialsDTO) {
        println("Received <$newUserCredentialsDto>")

        var userCredentials = UserCredentials()
        userCredentials.userId = newUserCredentialsDto.userId
        userCredentials.email = newUserCredentialsDto.email
        userCredentials.password = newUserCredentialsDto.password

        this.userCredentialsDao.save(userCredentials)
    }
}