package com.gmcn.tokens

import com.gmcn.tokens.dtos.NewUserCredentialsDTO
import org.springframework.stereotype.Component

@Component
class MessageReceiver {
    fun receiveMessage(newUserCreadentialsDto: NewUserCredentialsDTO) {
        println("Received <$newUserCreadentialsDto>")
    }
}