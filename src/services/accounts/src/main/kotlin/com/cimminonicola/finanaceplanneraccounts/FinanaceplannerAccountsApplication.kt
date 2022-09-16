package com.cimminonicola.finanaceplanneraccounts

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class FinanaceplannerAccountsApplication

fun main(args: Array<String>) {
    runApplication<FinanaceplannerAccountsApplication>(*args)

}
