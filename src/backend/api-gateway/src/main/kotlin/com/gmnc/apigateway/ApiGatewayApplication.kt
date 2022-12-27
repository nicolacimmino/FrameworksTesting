package com.gmnc.apigateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean


@SpringBootApplication
class ApiGatewayApplication {
    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes()
            .route(
                "tokens"
            ) { r: PredicateSpec ->
                r.path("/api/tokens")
                    .uri("lb://TOKENS-SERVICE")
            }
            .route(
                "api"
            ) { r: PredicateSpec ->
                r.path(
                    "/api/users/{user_id}/{resource_type}",
                    "/api/users/{user_id}/{resource_type}/{resource_id}"
                )
                    .uri("lb://USER-ASSETS-SERVICE")
            }
            .route(
                "api"
            ) { r: PredicateSpec ->
                r.path(
                    "/api/users/{user_id}",
                    "/api/users"
                )
                    .uri("lb://USERS-SERVICE")
            }
            .build()
    }
}

fun main(args: Array<String>) {
    runApplication<ApiGatewayApplication>(*args)
}
