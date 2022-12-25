package com.gmnc.serviceregistry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

// see: https://medium.com/@ocrnshn/microservices-with-spring-c30dfef8782b

@SpringBootApplication
@EnableEurekaServer
class ServerviceRegistryApplication

fun main(args: Array<String>) {
    runApplication<ServerviceRegistryApplication>(*args)
}
