package org.example.lab2_test.bookstore.router;

import org.example.lab2_test.bookstore.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class UserRouter {
    @Bean
    RouterFunction<ServerResponse> routeUser(UserHandler userHandler) {
        return RouterFunctions
                .route(RequestPredicates
                        .POST("/register").and(accept(MediaType.APPLICATION_JSON)), userHandler::registerUser)
                .andRoute(RequestPredicates
                        .GET("/users"), userHandler::getUsers);
    }
}
