package org.example.lab2_test.bookstore.controller;

import lombok.AllArgsConstructor;
import org.example.lab2_test.bookstore.entity.Book;
import org.example.lab2_test.bookstore.service.BookService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/book-api")
public class BookRestController {
    private final BookService bookService;

    @GetMapping
    public Flux<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Mono<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public Mono<Book> createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PatchMapping("/{id}")
    public Mono<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }
}
