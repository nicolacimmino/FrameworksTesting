package com.gmcn.tokens

import org.springframework.stereotype.Component

@Component
class ApplicationStatus {
    lateinit var authorizedUserId: String
}