package org.example.lab2_test.bookstore.router;

import org.example.lab2_test.bookstore.handler.GreetingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class GreetingRouter {

    @Bean
    RouterFunction<ServerResponse> routeGreeting(GreetingHandler greetingHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/hello").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::hello)
                .andRoute(RequestPredicates.GET("/"), greetingHandler::home)
                .andRoute(RequestPredicates.GET("/users"), greetingHandler::getClients);
    }
}
