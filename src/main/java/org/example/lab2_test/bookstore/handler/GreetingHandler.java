package org.example.lab2_test.bookstore.handler;

import org.example.lab2_test.bookstore.entity.Client;
import org.example.lab2_test.bookstore.entity.Greeting;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GreetingHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new Greeting("Hello, Spring")));
    }

    public Mono<ServerResponse> home(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("Welcome to the Book Order System!"));
    }

    public Mono<ServerResponse> getClients(ServerRequest request) {
        String start = request.queryParam("start").orElse("0");

        Flux<Client> clients = Flux.just(
                        new Client(1L, "Roman", "Chernukha", 21),
                        new Client(2L, "Ivanna", "Likh", 21),
                        new Client(3L, "Ivan", "Tkach", 19)
                )
                .skip(Long.parseLong(start))
                .take(2);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(clients, Client.class);
    }
}