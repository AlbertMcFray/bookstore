package org.example.lab2_test.bookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long orderId;
    private Long bookId;
    private Long clientId;
    private String clientName;
    private LocalDateTime orderDate;
}
