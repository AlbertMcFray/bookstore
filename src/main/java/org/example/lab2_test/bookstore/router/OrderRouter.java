package org.example.lab2_test.bookstore.router;

import org.example.lab2_test.bookstore.handler.OrderHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class OrderRouter {
    @Bean
    public RouterFunction<ServerResponse> routeOrder(OrderHandler orderHandler) {
        return RouterFunctions
                .route(RequestPredicates.
                        GET("/books").and(accept(MediaType.APPLICATION_JSON)), orderHandler::getBookList)
                .andRoute(RequestPredicates.
                        POST("/order").and(accept(MediaType.APPLICATION_JSON)), orderHandler::placeOrder);
    }
}