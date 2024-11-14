package org.example.lab2_test.bookstore.service;

import lombok.AllArgsConstructor;
import org.example.lab2_test.bookstore.entity.Book;
import org.example.lab2_test.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Mono<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Mono<Book> createBook(Book book) {
        return bookRepository.save(book);
    }

    public Mono<Book> updateBook(Long id, Book book) {
        return bookRepository.findById(id)
                .flatMap(existingBook -> {
                    existingBook.setTitle(book.getTitle());
                    existingBook.setAuthor(book.getAuthor());
                    existingBook.setPrice(book.getPrice());
                    return bookRepository.save(existingBook);
                });
    }

    public Mono<Void> deleteBook(Long id) {
        return bookRepository.deleteById(id);
    }
}
