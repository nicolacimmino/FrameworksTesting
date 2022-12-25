package com.gmnc.apigateway

import com.netflix.discovery.EurekaClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean


@SpringBootApplication
class ApiGatewayApplication {
    @Autowired
    val eurekaClient: EurekaClient? = null

    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes()
            .route(
                "tokens"
            ) { r: PredicateSpec ->
                r.path("/api/tokens")
                    .uri(eurekaClient?.getNextServerFromEureka("TOKENS-SERVICE", false)?.homePageUrl)
            }
            .route(
                "api"
            ) { r: PredicateSpec ->
                r.path("/api/users/{user_id}/{resource_type}",
                    "/api/users/{user_id}/{resource_type}/{resource_id}")
                    .uri(eurekaClient?.getNextServerFromEureka("USER-ASSETS-SERVICE", false)?.homePageUrl)
            }
            .route(
                "api"
            ) { r: PredicateSpec ->
                r.path("/api/users/{user_id}")
                    .uri(eurekaClient?.getNextServerFromEureka("USERS-SERVICE", false)?.homePageUrl)
            }
            .build()
    }
}

fun main(args: Array<String>) {
    runApplication<ApiGatewayApplication>(*args)
}
