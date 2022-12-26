package com.gmcn.users

import org.springframework.stereotype.Component

// TODO: Investigate, I don't think this is the standard/best way to hold some application status

@Component
class ApplicationStatus {
    lateinit var authorizedUserId: String
}