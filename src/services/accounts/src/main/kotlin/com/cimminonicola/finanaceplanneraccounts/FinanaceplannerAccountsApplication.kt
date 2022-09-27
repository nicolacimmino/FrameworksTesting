package com.cimminonicola.finanaceplanneraccounts

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class, SecurityAutoConfiguration::class])
class FinanaceplannerAccountsApplication

fun main(args: Array<String>) {
    runApplication<FinanaceplannerAccountsApplication>(*args)
}

