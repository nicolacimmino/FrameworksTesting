package com.gmnc.serviceregistry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class ServerviceRegistryApplication

fun main(args: Array<String>) {
    runApplication<ServerviceRegistryApplication>(*args)
}
