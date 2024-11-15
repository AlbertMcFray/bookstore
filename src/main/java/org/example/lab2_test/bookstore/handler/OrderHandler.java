package org.example.lab2_test.bookstore.handler;

import lombok.RequiredArgsConstructor;
import org.example.lab2_test.bookstore.entity.Book;
import org.example.lab2_test.bookstore.entity.Order;
import org.example.lab2_test.bookstore.repository.BookRepository;
import org.example.lab2_test.bookstore.repository.OrderRepository;
import org.example.lab2_test.bookstore.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OrderHandler {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Mono<ServerResponse> getBookList(ServerRequest request) {
        Flux<Book> booksFlux = bookRepository.findAll();

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(booksFlux, Book.class);
    }

    public Mono<ServerResponse> placeOrder(ServerRequest request) {
        return request.bodyToMono(Order.class)
                .flatMap(order ->
                        userRepository.findById(order.getUserId())
                                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                                .flatMap(user ->
                                        bookRepository.findById(order.getBookId())
                                                .switchIfEmpty(Mono.error(new RuntimeException("Book not found")))
                                                .flatMap(book -> {
                                                    order.setOrderDate(LocalDateTime.now());
                                                    return orderRepository.save(order);
                                                })
                                )
                )
                .flatMap(savedOrder -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedOrder))
                .onErrorResume(e -> ServerResponse
                        .badRequest()
                        .bodyValue(e.getMessage()));
    }
}
