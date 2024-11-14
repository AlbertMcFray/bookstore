package org.example.lab2_test.bookstore.handler;

import org.example.lab2_test.bookstore.entity.Book;
import org.example.lab2_test.bookstore.entity.Client;
import org.example.lab2_test.bookstore.entity.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class OrderHandler {

    private final List<Book> books = List.of(
            new Book(1L, "Spring in Action", "Craig Walls", 45.0),
            new Book(2L, "Java Concurrency in Practice", "Brian Goetz", 55.0),
            new Book(3L, "Effective Java", "Joshua Bloch", 50.0)
    );

    private final List<Client> clients = List.of(
            new Client(1L, "Roman", "Chernukha", 21),
            new Client(2L, "Ivanna", "Likh", 21),
            new Client(3L, "Ivan", "Tkach", 19)
    );

    private final Random random = new Random();

    public Mono<ServerResponse> getBookList(ServerRequest request) {
        Flux<Book> booksFlux = Flux.fromIterable(books);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(booksFlux, Book.class);
    }

    public Mono<ServerResponse> placeOrder(ServerRequest request) {
        Mono<Order> orderMono = request.bodyToMono(Order.class)
                .map(order -> {
                    Book randomBook = books.get(random.nextInt(books.size()));
                    Client randomClient = clients.get(random.nextInt(clients.size()));

                    order.setBookId(randomBook.getId());
                    order.setClientName(randomClient.getFirstName() + " " + randomClient.getLastName());
                    order.setOrderDate(LocalDateTime.now());
                    return order;
                });
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderMono, Order.class);
    }
}
