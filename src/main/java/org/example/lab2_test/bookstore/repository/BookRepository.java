package org.example.lab2_test.bookstore.repository;

import org.example.lab2_test.bookstore.entity.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, Long> {
}
